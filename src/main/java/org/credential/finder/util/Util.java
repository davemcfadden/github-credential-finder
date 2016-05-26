package org.credential.finder.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Repository;
import org.springframework.stereotype.Component;

@Component
public class Util {

  private final static Logger LOGGER = Logger.getLogger(Util.class);

  public static List<String> repositoryUserContentUrl(Repository repo, String branch,
      List<String> fileUrls) {
    List<String> urls = new ArrayList<String>();
    for (String singlePath : fileUrls) {
      String url = StringUtils.join(repo.getHtmlUrl(), "/", branch, "/");
      url = url.replace("github.com", "raw.githubusercontent.com");
      urls.add(StringUtils.join(url, singlePath));
    }
    return urls;
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

  public static void fileCleanUp(List<String> filePaths) {
    for (String filepath : filePaths) {
      File f = new File(filepath);
      if (f.exists()) {
        f.delete();
      }
    }
  }
}


