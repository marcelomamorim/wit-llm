/*
 * @test
 * @summary Test default constructor of LongSummaryStatistics
 * @run main LongSummaryStatisticsWitupTest
 */
public class LongSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        java.util.LongSummaryStatistics stats = new java.util.LongSummaryStatistics();
        if (stats.getCount() != 0) throw new RuntimeException("Expected count 0");
        if (stats.getSum() != 0L) throw new RuntimeException("Expected sum 0");
        if (stats.getMin() != Long.MAX_VALUE) throw new RuntimeException("Expected min Long.MAX_VALUE");
        if (stats.getMax() != Long.MIN_VALUE) throw new RuntimeException("Expected max Long.MIN_VALUE");
        if (stats.getAverage() != 0.0) throw new RuntimeException("Expected average 0.0");
    }
}
