package org.credential.finder.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Repository;
import org.springframework.stereotype.Component;

@Component
public class Util {

  private final static Logger LOGGER = Logger.getLogger(Util.class);

  public String repositoryUserContentUrl(Repository repo, String branch) {
    String url = repo.getHtmlUrl() + "/" + branch + "/";
    return url.replace("github.com", "raw.githubusercontent.com");
  }

  public List<String> getHazardStrings() {
    try {
      return Files.readAllLines(Paths.get("src/main/resources/flaggers.txt"));
    } catch (IOException e) {
      LOGGER.error(e);
    }
    return Collections.emptyList();
  }
}


