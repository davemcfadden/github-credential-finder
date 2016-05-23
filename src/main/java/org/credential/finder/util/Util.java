package org.credential.finder.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Repository;
import org.springframework.stereotype.Component;

@Component
public class Util {

  private final static Logger LOGGER = Logger.getLogger(Util.class);

  public static String repositoryUserContentUrl(Repository repo, String branch) {
    String url = repo.getHtmlUrl() + "/" + branch + "/";
    return url.replace("github.com", "raw.githubusercontent.com");
  }

  public static List<String> getHazardStrings() {
    try {
      return Files.readAllLines(Paths.get("src/main/resources/flaggers.txt"));
    } catch (IOException e) {
      LOGGER.error(e);
    }
    return Collections.emptyList();
  }

  public static List<String> streamFile(String file) {
    try {
      return Files.lines(Paths.get(file)).collect(Collectors.toList());
    } catch (IOException e) {
      LOGGER.error("Unable to parse file: " + file + "\n" + e);
      return Collections.emptyList();
    }
  }
}


