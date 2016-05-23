package org.credential.finder.pojo;

public class Violation {

  public Violation() {}
  
  public Violation(String fileUrl, int lineNumber) {
    this.fileUrl = fileUrl;
    this.lineNumber = lineNumber;
  }
  
  private String fileUrl;
  private int lineNumber;

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  @Override
  public String toString() {
    return "Violation [fileUrl=" + fileUrl + ", lineNumber=" + lineNumber + "]";
  }

}
