/*
 * @test
 * @summary Basic tests for IntSummaryStatistics constructor
 * @run main IntSummaryStatisticsWitupTest
 */

import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testConstructorWithValidValues();
        testConstructorWithInvalidCount();
        testConstructorWithInvalidMinMax();
    }

    static void testDefaultConstructor() {
        IntSummaryStatistics stats = new IntSummaryStatistics();
        assert stats != null : "Default constructor should create non-null object";
        assert stats.getCount() == 0 : "Count should be zero";
        assert stats.getSum() == 0 : "Sum should be zero";
        assert stats.getMin() == Integer.MAX_VALUE : "Min should be Integer.MAX_VALUE";
        assert stats.getMax() == Integer.MIN_VALUE : "Max should be Integer.MIN_VALUE";
    }

    static void testConstructorWithValidValues() {
        IntSummaryStatistics stats = new IntSummaryStatistics(10, 1, 5, 15);
        assert stats != null : "Constructor with valid values should create non-null object";
        assert stats.getCount() == 10 : "Count should be 10";
        assert stats.getSum() == 15 : "Sum should be 15";
        assert stats.getMin() == 1 : "Min should be 1";
        assert stats.getMax() == 5 : "Max should be 5";
    }

    static void testConstructorWithInvalidCount() {
        try {
            new IntSummaryStatistics(-1, 0, 0, 0);
            assert false : "Expected IllegalArgumentException for negative count";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithInvalidMinMax() {
        try {
            new IntSummaryStatistics(1, 10, 5, 10);
            assert false : "Expected IllegalArgumentException for min > max";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
