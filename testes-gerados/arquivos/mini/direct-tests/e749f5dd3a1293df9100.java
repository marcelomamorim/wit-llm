/*
 * @test
 * @summary Test TemporalAdjusters.ofDateAdjuster with various UnaryOperator<LocalDate> implementations
 * @run main TemporalAdjustersWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoField;
import java.util.function.UnaryOperator;

public class TemporalAdjustersWitupTest {
    public static void main(String[] args) {
        testNullUnaryOperator();
        testIdentityAdjuster();
        testAddDaysAdjuster();
        testAdjusterChangesMonth();
    }

    private static void testNullUnaryOperator() {
        try {
            TemporalAdjuster ta = java.time.temporal.TemporalAdjusters.ofDateAdjuster(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIdentityAdjuster() {
        UnaryOperator<LocalDate> identity = new UnaryOperator<LocalDate>() {
            public LocalDate apply(LocalDate t) {
                return t;
            }
        };
        TemporalAdjuster ta = java.time.temporal.TemporalAdjusters.ofDateAdjuster(identity);
        LocalDate date = LocalDate.of(2018, 9, 25);
        Temporal result = ta.adjustInto(date);
        if (!result.equals(date)) {
            throw new AssertionError("Identity adjuster should return same date");
        }
    }

    private static void testAddDaysAdjuster() {
        UnaryOperator<LocalDate> addFiveDays = new UnaryOperator<LocalDate>() {
            public LocalDate apply(LocalDate t) {
                return t.plusDays(5);
            }
        };
        TemporalAdjuster ta = java.time.temporal.TemporalAdjusters.ofDateAdjuster(addFiveDays);
        LocalDate date = LocalDate.of(2018, 9, 25);
        Temporal result = ta.adjustInto(date);
        LocalDate expected = date.plusDays(5);
        if (!result.equals(expected)) {
            throw new AssertionError("Adjuster should add 5 days");
        }
    }

    private static void testAdjusterChangesMonth() {
        UnaryOperator<LocalDate> setFirstDayOfNextMonth = new UnaryOperator<LocalDate>() {
            public LocalDate apply(LocalDate t) {
                return t.withDayOfMonth(1).plusMonths(1);
            }
        };
        TemporalAdjuster ta = java.time.temporal.TemporalAdjusters.ofDateAdjuster(setFirstDayOfNextMonth);
        LocalDate date = LocalDate.of(2018, 9, 25);
        Temporal result = ta.adjustInto(date);
        LocalDate expected = LocalDate.of(2018, 10, 1);
        if (!result.equals(expected)) {
            throw new AssertionError("Adjuster should set to first day of next month");
        }
    }
}
