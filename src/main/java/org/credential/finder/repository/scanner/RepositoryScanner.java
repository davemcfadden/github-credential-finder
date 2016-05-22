package org.credential.finder.repository.scanner;

import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.credential.finder.analyzer.FileAnalyzer;
import org.credential.finder.config.GitConfig;
import org.eclipse.egit.github.core.Blob;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryScanner {

  @Autowired
  private FileAnalyzer analyzer;

  @Autowired
  private GitConfig config;
  
  private final static String DIRECTORY = "dir";
  private final static String FILE = "file";
  private String encoding = Blob.ENCODING_UTF8;

  private final static Logger LOGGER = Logger.getLogger(RepositoryScanner.class);

  public void scanRepository(List<Repository> repos) {
    for (Repository repo : repos) {
      List<RepositoryContents> contents = null;
      System.out.println(repo.getName());
      try {
        // empty string for path so we start at the root
        contents = callContentsService(repo, "");
        contentsScanner(contents, repo);
      } catch (IOException e) {
        LOGGER.error("Cannot get contents from repo " + repo.getName() + " : " + e);
      }
    }
  }

  private void contentsScanner(List<RepositoryContents> contents, Repository repo) {
    for (RepositoryContents content : contents) {
      if (content.getType().equals(DIRECTORY)) {
        try {
          scanDirectory(content, repo);
        } catch (IOException e) {
          LOGGER.error("Failed to scan directory : " + e);
        }
      } else if (content.getType().equals(FILE)) {
        scanFile(content , repo);
      }
    }
  }

  //TODO try and speed this up
  private void scanDirectory(RepositoryContents content, Repository repo) throws IOException {
    List<RepositoryContents> contents = callContentsService(repo, content.getPath());
    contentsScanner(contents, repo);
  }

  private List<RepositoryContents> callContentsService(Repository repo, String path)
      throws IOException {
    ContentsService contentsService = new ContentsService(config.getClient());
    List<RepositoryContents> contents;
    contents = contentsService.getContents(repo, path);
    return contents;
  }
  
  //TODO create internal queue to pop file contents onto a queue which can be
  //analysed by another thread running FileAnalyzer
  private void scanFile(RepositoryContents content, Repository repo) {
    LOGGER.info("We need to scan this file and look for credentials..");
    //if(content.getName().equalsIgnoreCase("readme.md")){
        DataService dataService = new DataService();
        String fileAsText = null;
        try {
        	Blob response = dataService.getBlob(repo, content.getSha());
        	//lets validate our response type before trying to decode..
            if (response.getEncoding().equals(Blob.ENCODING_BASE64)
                && encoding != null && encoding.equalsIgnoreCase(Blob.ENCODING_UTF8)) {
            	fileAsText = new String(Base64.decodeBase64(response.getContent()));
                System.out.println("Decoded value is " + new String(fileAsText));
                analyzer.analyseFile(fileAsText,repo, content);
            }
		} catch (IOException e) {
			LOGGER.error("Error in getting file data : " + e);;
		}
  }
}

