package org.credential.finder.repository;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.credential.finder.constants.FinderConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FileDownloaderTest {


  private List<String> repoUrls;
  private List<String> fileNames;

  @Before
  public void setup() {
    repoUrls = createListOfUrls();
    fileNames = getFileNames();
  }
  
  @After
  public void cleanUp(){
    for(String fileName : fileNames){
      File f = new File(StringUtils.join(FinderConstants.DOWNLOAD_DESTINATION, fileName));
      if(f.exists()){
        f.delete();
      }
    }
  }


  @Test
  @Ignore
  public void downloadFileTest() {
    FileDownloader.downloadFile(repoUrls);
    for (int i = 0; i < repoUrls.size(); i++) {
      File f = new File(StringUtils.join(FinderConstants.DOWNLOAD_DESTINATION, fileNames.get(i)));
      assertTrue(f.exists());
      assertTrue(!f.isDirectory());
    }

  }


  private List<String> createListOfUrls() {
    List<String> urls = new ArrayList<String>();
    urls.add(
        "https://raw.githubusercontent.com/davemcfadden/aws-dynamo-service/master/src/main/java/org/aws/dynamo/Application.java");
    urls.add(
        "https://raw.githubusercontent.com/davemcfadden/aws-dynamo-service/master/src/main/java/org/aws/dynamo/service/DynamoService.java");
    urls.add(
        "https://raw.githubusercontent.com/CBrowne/git-graph/master/src/main/java/git/graph/Application.java");
    urls.add(
        "https://raw.githubusercontent.com/CBrowne/git-graph/master/src/main/resources/log4j.properties");
    return urls;
  }


  private List<String> getFileNames() {
    List<String> fileNames = new ArrayList<String>();
    for (String url : repoUrls) {
      fileNames.add(StringUtils.substring(url, StringUtils.lastIndexOf(url, "/") + 1));
    }
    return fileNames;
  }

}
