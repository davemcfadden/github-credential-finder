package org.credential.finder.analyzer;

import org.junit.Test;

public class FileAnalyzerTest {

  @Test
  public void test1() {
    FileAnalyzer.findIssues("src/test/resources/example.file").forEach(System.out::println);
  }

}
