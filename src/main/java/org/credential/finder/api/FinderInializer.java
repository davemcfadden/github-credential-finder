package org.credential.finder.api;

import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FinderInializer{
	
	@Value("${github.username}")
	String username;


	@Value("${github.password}")
	String password;

	public void run(){

		//OAuth2 token authentication
		GitHubClient client = new GitHubClient();
		client.setCredentials(username, password);
		
		RepositoryService service = new RepositoryService(client);
		try {
			for (Repository repo : service.getRepositories("defunkt"))
			  System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
