package org.credential.finder.config;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitConfig {

  private GitHubClient client;
  private RepositoryService service;

  @Autowired
  public GitConfig(@Value("${github.username}") String username,
      @Value("${github.password}") String password) {
    client = new GitHubClient();
    this.client.setCredentials(username, password);
    this.service = new RepositoryService(client);
  }

  public RepositoryService getService() {
    return service;
  }

  public void setService(RepositoryService service) {
    this.service = service;
  }

  public GitHubClient getClient() {
    return client;
  }

  public void setClient(GitHubClient client) {
    this.client = client;
  }

}
