/*
 * @test
 * @summary Verifies the observable state of an IntSummaryStatistics created by the no-arg constructor
 * @run main IntSummaryStatisticsWitupTest
 */

import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        IntSummaryStatistics stats = new IntSummaryStatistics();

        check(stats.getCount() == 0L, "count must be 0 for an empty instance");
        check(stats.getSum() == 0L, "sum must be 0 for an empty instance");
        check(stats.getMin() == Integer.MAX_VALUE, "min must be Integer.MAX_VALUE for an empty instance");
        check(stats.getMax() == Integer.MIN_VALUE, "max must be Integer.MIN_VALUE for an empty instance");
        check(stats.getAverage() == 0.0d, "average must be 0.0 for an empty instance");

        stats.accept(5);
        check(stats.getCount() == 1L, "count must be 1 after one accept");
        check(stats.getSum() == 5L, "sum must be 5 after accepting 5");
        check(stats.getMin() == 5, "min must be 5 after accepting 5");
        check(stats.getMax() == 5, "max must be 5 after accepting 5");

        IntSummaryStatistics fresh = new IntSummaryStatistics();
        check(fresh.getCount() == 0L, "a new instance must not share state with a previous one");
    }
}
