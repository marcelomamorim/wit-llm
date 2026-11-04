/*
 * @test
 * @summary Test default constructor of IntSummaryStatistics
 * @run main IntSummaryStatisticsWitupTest
 */

import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        IntSummaryStatistics stats = new IntSummaryStatistics();
        if (stats.getCount() != 0) throw new AssertionError("Count should be 0");
        if (stats.getSum() != 0) throw new AssertionError("Sum should be 0");
        if (stats.getMin() != Integer.MAX_VALUE) throw new AssertionError("Min should be Integer.MAX_VALUE");
        if (stats.getMax() != Integer.MIN_VALUE) throw new AssertionError("Max should be Integer.MIN_VALUE");
        if (stats.getAverage() != 0.0) throw new AssertionError("Average should be 0.0");
    }
}
