package org.credential.finder.util;

import static org.junit.Assert.assertEquals;

import org.eclipse.egit.github.core.Repository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class UtilTest {

  private Repository repo;

  private String validFilePath;

  @Before
  public void setup() {
    repo = new Repository();
    repo.setHtmlUrl("https://github.com/davemcfadden/github-credential-finder/");
    validFilePath = "src/test/resources/example.file";
  }

  @Test
  public void testGetHazardStrings() {
    assertEquals("password", Util.getHazardStrings().get(3));
  }

  @Test
  public void testRepositoryUserContentUrl() {
    assertEquals("https://raw.githubusercontent.com/davemcfadden/github-credential-finder/master/",
        Util.repositoryUserContentUrl(repo, "master"));
  }

  @Test
  public void testStreamFileInvalidPath() {
    assertEquals(0, Util.streamFile("not/real/path").size());
  }

  @Test
  public void testStreamFileValidPath() {
    assertEquals(10, Util.streamFile(validFilePath).size());
    assertEquals("key", Util.streamFile(validFilePath).get(4));
  }


}
