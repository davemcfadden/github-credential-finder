package org.credential.finder.pojo;

public class Violation {

  public Violation() {}

  public Violation(String fileUrl, String context, int lineNumber) {
    this.fileUrl = fileUrl;
    this.context = context;
    this.lineNumber = lineNumber;
  }

  private String fileUrl;
  private String context;
  private int lineNumber;

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  @Override
  public String toString() {
    return "Violation [fileUrl=" + fileUrl + ", context=" + context + ", lineNumber=" + lineNumber
        + "]";
  }

}
