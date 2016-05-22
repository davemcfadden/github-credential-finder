package org.credential.finder.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.credential.finder.config.GitConfig;
import org.credential.finder.repository.scanner.RepositoryScanner;
import org.eclipse.egit.github.core.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinderInializer {

  @Autowired
  private RepositoryScanner repoScanner;

  @Autowired
  private GitConfig config;

  private final static Logger LOGGER = Logger.getLogger(FinderInializer.class);

  public void run() {
    List<Repository> repos = new ArrayList<Repository>();
    try {
      // just grabbing a single repo for testing
      repos.addAll(config.getService().getRepositories("davemcfadden"));
      //repos.add(config.getService().getRepository("davemcfadden", "aws-dynamo-service"));
    } catch (IOException e) {
      LOGGER.error("Failed to retrieve repos : " + e);
    }
    repoScanner.scanRepository(repos);
  }
}

