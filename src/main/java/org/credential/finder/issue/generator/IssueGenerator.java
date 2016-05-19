package org.credential.finder.issue.generator;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.springframework.stereotype.Service;

@Service
public class IssueGenerator {

  private final static Logger LOGGER = Logger.getLogger(IssueGenerator.class);

  public Issue createIssue(Repository repo, GitHubClient client) {
    IssueService issueService = new IssueService(client);
    Issue issue = new Issue();
    issue.setTitle("Non encrypted credentials");
    issue.setAssignee(repo.getOwner());
    issue.setBody("Test issue creation");
    issue.setCreatedAt(new Date());

    Issue response = null;
    try {
      response = issueService.createIssue(repo, issue);
    } catch (IOException e) {
      LOGGER.error("Failed to create issue : " + e);
    }
    return response;
  }

}
