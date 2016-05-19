package org.credential.finder.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.credential.finder.issue.generator.IssueGenerator;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FinderInializer{
	
	@Value("${github.username}")
	String username;

	@Value("${github.password}")
	String password;
	
	@Autowired
	IssueGenerator issueGenerator;
	
	final static Logger LOGGER = Logger.getLogger(FinderInializer.class);

	public void run(){
		GitHubClient client = new GitHubClient();
		client.setCredentials(username, password);
		RepositoryService service = new RepositoryService(client);
		List<Repository> repos = new ArrayList<Repository>();
		try {
			repos = service.getRepositories("davemcfadden");
		} catch (IOException e) {
			LOGGER.error("Failed to retrieve repos : " + e);
		}		
		scanRepository(client, repos);
	}

	private void scanRepository(GitHubClient client, List<Repository> repos) {
		for (Repository repo : repos ){
			System.out.println(repo.getName());
			if(repo.getName().equals("github-credential-finder")){
				issueGenerator.createIssue(repo,client);
			}
		}
	}
}
	
