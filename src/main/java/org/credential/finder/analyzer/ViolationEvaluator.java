package org.credential.finder.analyzer;

import org.credential.finder.pojo.Violation;

public class ViolationEvaluator {

  public static boolean trueViolation(Violation violation) {
    return true;
  }

  public static boolean noQuadCharAdjacency(String context) {
    char[] arr = context.toCharArray();
    for (int i = 0; i < arr.length - 3; i++) {
      if ((arr[i] == arr[i + 1]) && (arr[i + 1] == arr[i + 2]) && (arr[i + 2] == arr[i + 3])) {
        return false;
      }
    }
    return true;
  }

}
