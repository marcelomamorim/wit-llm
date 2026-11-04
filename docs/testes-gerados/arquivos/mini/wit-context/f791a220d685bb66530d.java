/*
 * @test
 * @summary Test java.time.temporal.TemporalAdjusters.firstInMonth(DayOfWeek) for null argument and normal usage
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {
    public static void main(String[] args) {
        testFirstInMonthNull();
        testFirstInMonthValid();
    }

    private static void testFirstInMonthNull() {
        try {
            TemporalAdjusters.firstInMonth(null);
            throw new AssertionError("Expected NullPointerException for null dayOfWeek");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testFirstInMonthValid() {
        for (DayOfWeek dow : DayOfWeek.values()) {
            TemporalAdjuster adj = TemporalAdjusters.firstInMonth(dow);
            if (adj == null) {
                throw new AssertionError("TemporalAdjuster must not be null for dayOfWeek: " + dow);
            }
        }
    }
}
