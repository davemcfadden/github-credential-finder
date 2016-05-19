package org.credential.finder.util;

import org.eclipse.egit.github.core.Repository;

public class Util {

  public static String repositoryUserContentUrl(Repository repo, String branch) {
    String url = repo.getHtmlUrl() + "/" + branch + "/";
    return url.replace("github.com", "raw.githubusercontent.com");
  }



}
