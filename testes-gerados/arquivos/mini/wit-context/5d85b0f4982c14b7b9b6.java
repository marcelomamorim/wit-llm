/*
 * @test
 * @summary Test default constructor of IntSummaryStatistics for initial state correctness
 * @run main IntSummaryStatisticsWitupTest
 */

import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        IntSummaryStatistics stats = new IntSummaryStatistics();

        // After default construction, count should be 0
        if (stats.getCount() != 0) {
            throw new AssertionError("Expected count 0 but got " + stats.getCount());
        }

        // Sum should be 0
        if (stats.getSum() != 0) {
            throw new AssertionError("Expected sum 0 but got " + stats.getSum());
        }

        // Min should be Integer.MAX_VALUE
        if (stats.getMin() != Integer.MAX_VALUE) {
            throw new AssertionError("Expected min Integer.MAX_VALUE but got " + stats.getMin());
        }

        // Max should be Integer.MIN_VALUE
        if (stats.getMax() != Integer.MIN_VALUE) {
            throw new AssertionError("Expected max Integer.MIN_VALUE but got " + stats.getMax());
        }

        // Average should be 0.0 when count is zero
        if (stats.getAverage() != 0.0) {
            throw new AssertionError("Expected average 0.0 but got " + stats.getAverage());
        }
    }
}
