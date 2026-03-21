package br.unb.cic.witupllmautogen.analysis.fixture;

public class ThrowingFixture {

  public int failWhenNegative(final int value) {
    if (value < 0) {
      throw new IllegalArgumentException("negative values are not allowed");
    }
    return value;
  }

  public int failWhenZeroOrOne(final int value) {
    if (value == 0) {
      throw new IllegalStateException("zero is not allowed");
    }
    if (value == 1) {
      throw new RuntimeException("one is not allowed");
    }
    return value;
  }
}
