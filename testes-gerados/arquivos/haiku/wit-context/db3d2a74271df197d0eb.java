/*
 * @test
 * @summary Verifies the observable state of an instance created by the no-arg LongSummaryStatistics constructor
 * @run main LongSummaryStatisticsWitupTest
 */
public class LongSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        LongSummaryStatistics stats = new LongSummaryStatistics();
        check(stats.getCount() == 0L, "count must be 0");
        check(stats.getSum() == 0L, "sum must be 0");
        check(stats.getMin() == Long.MAX_VALUE, "min must be Long.MAX_VALUE");
        check(stats.getMax() == Long.MIN_VALUE, "max must be Long.MIN_VALUE");
        check(Double.compare(stats.getAverage(), 0.0d) == 0, "average must be 0.0");

        stats.accept(5L);
        check(stats.getCount() == 1L, "count must be 1 after one accept");
        check(stats.getSum() == 5L, "sum must be 5 after accept(5)");
        check(stats.getMin() == 5L, "min must be 5 after accept(5)");
        check(stats.getMax() == 5L, "max must be 5 after accept(5)");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
