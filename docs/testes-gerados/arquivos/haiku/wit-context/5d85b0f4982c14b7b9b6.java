/*
 * @test
 * @summary Verifies the observable state of a new IntSummaryStatistics created by the no-argument constructor
 * @run main IntSummaryStatisticsWitupTest
 */

import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsWitupTest {

    public static void main(String[] args) {
        IntSummaryStatistics s = new IntSummaryStatistics();

        check(s.getCount() == 0L, "count of empty instance must be 0");
        check(s.getSum() == 0L, "sum of empty instance must be 0");
        check(s.getMin() == Integer.MAX_VALUE, "min of empty instance must be Integer.MAX_VALUE");
        check(s.getMax() == Integer.MIN_VALUE, "max of empty instance must be Integer.MIN_VALUE");
        check(s.getAverage() == 0.0d, "average of empty instance must be 0.0");

        s.accept(5);
        s.accept(-3);

        check(s.getCount() == 2L, "count after two accepts must be 2");
        check(s.getSum() == 2L, "sum after accepting 5 and -3 must be 2");
        check(s.getMin() == -3, "min after accepting 5 and -3 must be -3");
        check(s.getMax() == 5, "max after accepting 5 and -3 must be 5");
        check(s.getAverage() == 1.0d, "average after accepting 5 and -3 must be 1.0");

        IntSummaryStatistics t = new IntSummaryStatistics();
        check(t.getCount() == 0L, "new instance must not share state with previous instance");
        check(t.getMin() == Integer.MAX_VALUE, "new instance min must remain Integer.MAX_VALUE");
        check(t.getMax() == Integer.MIN_VALUE, "new instance max must remain Integer.MIN_VALUE");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
