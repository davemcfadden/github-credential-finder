package org.credential.finder.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.credential.finder.pojo.Violation;
import org.credential.finder.util.Util;
import org.springframework.stereotype.Component;

@Component
public class FileAnalyzer {

  private final static Logger LOGGER = Logger.getLogger(FileAnalyzer.class);

  private static final List<String> hazardStrings = Util.getHazardStrings();

  /**
   * Scan the file provided and identify strings that may contain private information. Evaluate the
   * candidates and return a list of potential violations.
   * 
   * @param filePath
   * @return list of violations
   */
  public static void findIssues(List<String> filePaths) {
    List<Violation> issues = new ArrayList<>();
    for (String filePath : filePaths) {
      findPotentialViolations(filePath);
      issues = evaluateAndFilter(issues);
    }
    // lets open an issue from here
    issues.forEach(issue -> LOGGER.debug("Opening issue for : " + issue));
  }

  /**
   * Retrieve a list of strings representing all lines of the file provided. Evaluate each line to
   * see if it contains hazard terms listed in flaggers.txt. Return a list of Violation objects
   * describing the potentially exposed parts of the file.
   * 
   * @param filePath
   * @return list of violations
   */
  private static List<Violation> findPotentialViolations(String filePath) {
    List<Violation> violations = new ArrayList<Violation>();
    List<String> lines = Util.streamFile(filePath);
    for (String hazard : hazardStrings) {
      for (String line : lines) {
        if (line.contains(hazard)) {
          violations.add(new Violation(filePath, line, hazard, (lines.indexOf(line) + 1)));
        }
      }
    }
    violations.forEach(v -> LOGGER.debug("Potential violations found\n" + v.toString()));
    return violations;
  }


  /**
   * Brutally crude evaluation and filter.
   * 
   * @param issues
   * @return list of filtered violations
   */
  private static List<Violation> evaluateAndFilter(List<Violation> issues) {
    return issues.stream().filter(i -> ViolationEvaluator.trueViolation(i))
        .collect(Collectors.toList());
  }


}
