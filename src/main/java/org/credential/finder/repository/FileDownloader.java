package org.credential.finder.repository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.credential.finder.constants.FinderConstants;

public class FileDownloader {

  private final static Logger LOGGER = Logger.getLogger(FileDownloader.class);

  public static List<String> downloadFile(List<String> repoUrls) {
    List<String> localFilePaths = new ArrayList<String>();
    for (String url : repoUrls) {
      String fileName = StringUtils.substring(url, StringUtils.lastIndexOf(url, "\\") + 1);
      try {
        URL link = new URL(url);
        FileUtils.copyURLToFile(link, new File(FinderConstants.DOWNLOAD_DESTINATION, fileName));
        localFilePaths.add(StringUtils.join(FinderConstants.DOWNLOAD_DESTINATION, fileName));
      } catch (IOException e) {
        LOGGER.error("Error when downloading file : " + fileName + ". Exception : " + e);
      }
    }
    return localFilePaths;
  }

}
