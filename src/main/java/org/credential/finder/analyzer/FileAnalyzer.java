package org.credential.finder.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.credential.finder.api.FinderInializer;
import org.credential.finder.issue.generator.IssueGenerator;
import org.credential.finder.pojo.Violation;
import org.credential.finder.util.Util;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileAnalyzer {

  private final static Logger LOGGER = Logger.getLogger(FinderInializer.class);

  @Autowired
  private IssueGenerator issueGenerator;

  private static final List<String> hazardStrings = Util.getHazardStrings();


  public void analyseFile(String file, Repository repo, RepositoryContents content) {
    // TODO lets fine the stings
    LOGGER.debug("Searching file : " + content.getName() + " for passwords");
    // issueGenerator.createIssue(repo,content);
  }

  public static List<Violation> findPotentialViolations(String file) {
    List<Violation> violations = new ArrayList<Violation>();
    List<String> lines = Util.streamFile(file);
    for (String hazard : hazardStrings) {
      for (String line : lines) {
        if (line.contains(hazard)) {
          violations.add(new Violation(file, line, (lines.indexOf(line) + 1)));
        }
      }
    }
    violations.forEach(v -> LOGGER.debug("Potential violations found\n" + v.toString()));
    return violations;
  }

}
