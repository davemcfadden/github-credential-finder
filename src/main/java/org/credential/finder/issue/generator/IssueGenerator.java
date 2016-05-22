package org.credential.finder.issue.generator;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.credential.finder.config.GitConfig;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueGenerator {

  @Autowired
  GitConfig config;

  private final static Logger LOGGER = Logger.getLogger(IssueGenerator.class);

  public Issue createIssue(Repository repo, RepositoryContents content) {
    IssueService issueService = new IssueService(config.getClient());
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
