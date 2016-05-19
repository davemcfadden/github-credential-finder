package org.credential.finder.repository.scanner;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.credential.finder.config.GitConfig;
import org.credential.finder.issue.generator.IssueGenerator;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.service.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryScanner {

  @Autowired
  private IssueGenerator issueGenerator;

  @Autowired
  private GitConfig config;

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
      if (repo.getName().equals("github-credential-finder")) {
        // issueGenerator.createIssue(repo,client);
      }
    }
  }

  private void contentsScanner(List<RepositoryContents> contents, Repository repo) {
    for (RepositoryContents content : contents) {
      // System.out.println(content.getType().toString());
      if (content.getType().equals("dir")) {
        try {
          scanDirectory(content, repo);
        } catch (IOException e) {
          LOGGER.error("Failed to scan directory : " + e);
        }
      } else if (content.getType().equals("file")) {
        scanFile(content);
      }
    }
  }

  private void scanDirectory(RepositoryContents content, Repository repo) throws IOException {
    LOGGER.info("We need to rescan this folder and look for files.." + content.getName());
    List<RepositoryContents> contents = callContentsService(repo, content.getPath());
    contentsScanner(contents, repo);
  }

  private void scanFile(RepositoryContents content) {
    LOGGER.info("We need to scan this file and look for credentials..");
    System.out.println(content.getName());
  }

  private List<RepositoryContents> callContentsService(Repository repo, String path)
      throws IOException {
    ContentsService contentsService = new ContentsService(config.getClient());
    List<RepositoryContents> contents;
    contents = contentsService.getContents(repo, path);
    return contents;
  }

}
