/*
 * @test
 * @summary Generated tests for LongSummaryStatistics constructors and observable state
 * @run main LongSummaryStatisticsWitupTest
 */
import java.util.LongSummaryStatistics;

public class LongSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        testDefaultConstructor();
        testAcceptAfterDefaultConstructor();
        testFourArgConstructorWithZeroCountIgnoresOtherArguments();
        testFourArgConstructorWithNonEmptyCount();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDefaultConstructor() {
        LongSummaryStatistics stats = new LongSummaryStatistics();
        check(stats.getCount() == 0L, "default count must be 0");
        check(stats.getSum() == 0L, "default sum must be 0");
        check(stats.getMin() == Long.MAX_VALUE, "default min must be Long.MAX_VALUE");
        check(stats.getMax() == Long.MIN_VALUE, "default max must be Long.MIN_VALUE");
        check(stats.getAverage() == 0.0d, "default average must be 0.0");
    }

    static void testAcceptAfterDefaultConstructor() {
        LongSummaryStatistics stats = new LongSummaryStatistics();
        stats.accept(7L);
        check(stats.getCount() == 1L, "count after one accept must be 1");
        check(stats.getSum() == 7L, "sum after accept(7) must be 7");
        check(stats.getMin() == 7L, "min after accept(7) must be 7");
        check(stats.getMax() == 7L, "max after accept(7) must be 7");
        check(stats.getAverage() == 7.0d, "average after accept(7) must be 7.0");
    }

    static void testFourArgConstructorWithZeroCountIgnoresOtherArguments() {
        LongSummaryStatistics stats = new LongSummaryStatistics(0L, 1L, 2L, 3L);
        check(stats.getCount() == 0L, "count must be 0");
        check(stats.getSum() == 0L, "sum must be 0 when count is 0");
        check(stats.getMin() == Long.MAX_VALUE, "min must be Long.MAX_VALUE when count is 0");
        check(stats.getMax() == Long.MIN_VALUE, "max must be Long.MIN_VALUE when count is 0");
        check(stats.getAverage() == 0.0d, "average must be 0.0 when count is 0");
    }

    static void testFourArgConstructorWithNonEmptyCount() {
        LongSummaryStatistics stats = new LongSummaryStatistics(3L, 1L, 9L, 15L);
        check(stats.getCount() == 3L, "count must be 3");
        check(stats.getSum() == 15L, "sum must be 15");
        check(stats.getMin() == 1L, "min must be 1");
        check(stats.getMax() == 9L, "max must be 9");
        check(stats.getAverage() == 5.0d, "average must be 5.0");
    }
}
