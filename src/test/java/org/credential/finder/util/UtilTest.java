package org.credential.finder.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {

  private Repository repo;

  private String validFilePath;
  
  private String filePath;
  
  @Before
  public void setup() {
    repo = new Repository();
    repo.setHtmlUrl("https://github.com/davemcfadden/github-credential-finder/");
    validFilePath = "src/test/resources/example.file";
    filePath = "src/main/java/org/credential/finder/Application.java";
  }

  @Test
  public void testGetHazardStrings() {
    assertEquals("password", Util.getHazardStrings().get(3));
  }

  @Test
  public void testRepositoryUserContentUrl() {
    List<String> filePaths = new ArrayList<String>();
    filePaths.add(filePath);
    List<String> reponseList = Util.repositoryUserContentUrl(repo, "master",filePaths);
    for(String response : reponseList){
      assertEquals("https://raw.githubusercontent.com/davemcfadden/github-credential-finder/master/" +filePath,
          response);
    }
    
  }

  @Test
  public void testStreamFileInvalidPath() {
    assertEquals(0, Util.streamFile("not/real/path").size());
  }

  @Test
  public void testStreamFileValidPath() {
    assertEquals(17, Util.streamFile(validFilePath).size());
    assertEquals("password", Util.streamFile(validFilePath).get(3));
  }


}
