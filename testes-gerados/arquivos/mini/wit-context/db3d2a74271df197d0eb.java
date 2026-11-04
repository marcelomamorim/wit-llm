/*
 * @test
 * @summary Test default constructor of LongSummaryStatistics for initial state correctness
 * @run main java.util.LongSummaryStatisticsWitupTest
 */

public class LongSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        java.util.LongSummaryStatistics stats = new java.util.LongSummaryStatistics();

        // After default construction, count should be 0
        if (stats.getCount() != 0) {
            throw new AssertionError("Expected count 0 but got " + stats.getCount());
        }

        // Sum should be 0
        if (stats.getSum() != 0L) {
            throw new AssertionError("Expected sum 0 but got " + stats.getSum());
        }

        // Min should be Long.MAX_VALUE
        if (stats.getMin() != Long.MAX_VALUE) {
            throw new AssertionError("Expected min Long.MAX_VALUE but got " + stats.getMin());
        }

        // Max should be Long.MIN_VALUE
        if (stats.getMax() != Long.MIN_VALUE) {
            throw new AssertionError("Expected max Long.MIN_VALUE but got " + stats.getMax());
        }

        // Average should be 0.0 when count is zero
        if (stats.getAverage() != 0.0) {
            throw new AssertionError("Expected average 0.0 but got " + stats.getAverage());
        }
    }
}
