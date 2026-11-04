/* @test    @summary Test for LongSummaryStatistics constructor behavior and exception handling    @run main LongSummaryStatisticsWitupTest */

import java.util.LongSummaryStatistics;

public class LongSummaryStatisticsWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testConstructorWithInvalidArguments();
    }

    private static void testDefaultConstructor() {
        LongSummaryStatistics stats = new LongSummaryStatistics();
        assert stats != null : "Default constructor returned null";
        assert stats.getCount() == 0 : "Initial count should be zero";
        assert stats.getMin() == Long.MAX_VALUE : "Initial min should be Long.MAX_VALUE";
        assert stats.getMax() == Long.MIN_VALUE : "Initial max should be Long.MIN_VALUE";
        assert stats.getSum() == 0 : "Initial sum should be zero";
    }

    private static void testConstructorWithInvalidArguments() {
        boolean exceptionThrown = false;
        try {
            new LongSummaryStatistics(-1, 0, 0, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor with negative count should throw IllegalArgumentException";

        exceptionThrown = false;
        try {
            new LongSummaryStatistics(1, 10, 5, 15); // min > max
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor with min > max should throw IllegalArgumentException";
    }
}
