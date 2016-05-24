package org.credential.finder.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.credential.finder.analyzer.FileAnalyzer;
import org.credential.finder.config.GitConfig;
import org.credential.finder.constants.FinderConstants;
import org.credential.finder.util.Util;
import org.eclipse.egit.github.core.Blob;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RepositoryScanner {

  @Autowired
  private GitConfig config;

  @Value("${ignore.file.extentions}")
  private String[] ignoredExtensions;

  @Value("${ignore.file.names}")
  private String[] ignoredFileNames;


  private List<String> filePaths = new ArrayList<String>();


  private final static Logger LOGGER = Logger.getLogger(RepositoryScanner.class);

  public void scanRepository(List<Repository> repos) {
    for (Repository repo : repos) {
      List<RepositoryContents> contents = null;
      filePaths.addAll(Collections.emptyList());
      System.out.println(repo.getName());
      try {
        // empty string for path so we start at the root
        contents = callContentsService(repo, "");
        contentsScanner(contents, repo);
      } catch (IOException e) {
        LOGGER.error("Cannot get contents from repo " + repo.getName() + " : " + e);
      }
     FileAnalyzer.findIssues(buildGithubUrls(repo, filePaths));
     Util.cleanUp(filePaths);
    }
  }

  /**
   * Will scan the current directory and identify which is a file and which is a folder if it is a
   * file, get the path and build a URL
   * 
   * @param contents
   * @param repo
   */

  private void contentsScanner(List<RepositoryContents> contents, Repository repo) {
    for (RepositoryContents content : contents) {
      if (content.getType().equals(FinderConstants.DIRECTORY)) {
        try {
          scanDirectory(content, repo);
        } catch (IOException e) {
          LOGGER.error("Failed to scan directory : " + e);
        }
      } else if (content.getType().equals(FinderConstants.FILE)) {
        // get file type
        String fileExtention = StringUtils.substring(content.getName(),
            StringUtils.lastIndexOf(content.getName(), ".") + 1);
        if (!Arrays.asList(ignoredExtensions).contains(fileExtention)
            && !Arrays.asList(ignoredFileNames).contains(content.getName())) {
          filePaths.add(content.getPath());
        }
      }
    }
  }


  /**
   * This calls Util.repositoryUserContentUrl method which will return the raw content URL for this
   * github repository. Once that path has been returned we will use common.io to download the file
   * to the local computer
   * 
   * @param repo
   * @param fileUrls
   * @return 
   */
  private List<String> buildGithubUrls(Repository repo, List<String> fileUrls) {
    List<String> localFilePaths = null;
    if (!CollectionUtils.isEmpty(fileUrls)) {
      List<String> urls = Util.repositoryUserContentUrl(repo, FinderConstants.MASTER, fileUrls);
      localFilePaths = FileDownloader.downloadFile(urls);
    }
    return localFilePaths;
  }

  private void scanDirectory(RepositoryContents content, Repository repo) throws IOException {
    List<RepositoryContents> contents = callContentsService(repo, content.getPath());
    contentsScanner(contents, repo);
  }

  /**
   * This will call the GitApi to get the contents of a repository path
   * 
   * @param repo
   * @param path
   * @return
   * @throws IOException
   */
  private List<RepositoryContents> callContentsService(Repository repo, String path)
      throws IOException {
    ContentsService contentsService = new ContentsService(config.getClient());
    List<RepositoryContents> contents;
    contents = contentsService.getContents(repo, path);
    return contents;
  }



  /**
   * This has been deprecated in favour of downloading the files locally and analyzing them. This
   * was due to the restriction on the number of API calls that can be made to GitHub
   * 
   * @param content
   * @param repo
   */
  @SuppressWarnings("unused")
  @Deprecated
  private void scanFile(RepositoryContents content, Repository repo) {
    LOGGER.info("We need to scan this file and look for credentials..");
    // if(content.getName().equalsIgnoreCase("readme.md")){
    DataService dataService = new DataService();
    String fileAsText = null;
    try {
      Blob response = dataService.getBlob(repo, content.getSha());
      // lets validate our response type before trying to decode..
      if (response.getEncoding().equals(Blob.ENCODING_BASE64) && FinderConstants.ENCODING != null
          && FinderConstants.ENCODING.equalsIgnoreCase(Blob.ENCODING_UTF8)) {
        fileAsText = new String(Base64.decodeBase64(response.getContent()));
        System.out.println("Decoded value is " + new String(fileAsText));
        // analyzer.analyseFile(fileAsText, repo, content);
      }
    } catch (IOException e) {
      LOGGER.error("Error in getting file data : " + e);;
    }
  }
}

