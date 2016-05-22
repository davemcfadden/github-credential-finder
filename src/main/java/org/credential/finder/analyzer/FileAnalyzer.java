package org.credential.finder.analyzer;

import java.util.List;

import org.apache.log4j.Logger;
import org.credential.finder.api.FinderInializer;
import org.credential.finder.issue.generator.IssueGenerator;
import org.credential.finder.util.Util;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileAnalyzer {
	
	@Autowired
	private Util util;
	
	@Autowired
	private IssueGenerator issueGenerator;
	
	private final static Logger LOGGER = Logger.getLogger(FinderInializer.class);

	
	public void analyseFile(String file, Repository repo, RepositoryContents content){
		List<String> hazardStrings = util.getHazardStrings();
		//TODO lets fine the stings
		LOGGER.debug("Searching file : " + content.getName() + " for passwords");
		
		//issueGenerator.createIssue(repo,content);
	}

}
