package org.credential.finder.analyzer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ViolationEvaluatorTest {

  @Test
  public void testQuadCharAdjacencyTrue() {
    assertTrue(ViolationEvaluator.noQuadCharAdjacency("abc111--"));
  }

  @Test
  public void testQuadCharAdjacencyFalse() {
    assertFalse(ViolationEvaluator.noQuadCharAdjacency("abc1111--"));
  }

}
