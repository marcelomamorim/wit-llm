/*
 * @test
 * @summary Test TemporalAdjusters.ofDateAdjuster with valid and invalid inputs
 * @run main TemporalAdjustersWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoField;
import java.util.function.UnaryOperator;

public class TemporalAdjustersWitupTest {
    public static void main(String[] args) {
        testNullDateBasedAdjuster();
        testAdjusterWithNullTemporal();
        testValidAdjuster();
    }

    private static void testNullDateBasedAdjuster() {
        try {
            TemporalAdjuster ta = java.time.temporal.TemporalAdjusters.ofDateAdjuster(null);
            throw new AssertionError("Expected NullPointerException for null dateBasedAdjuster");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testAdjusterWithNullTemporal() {
        UnaryOperator<LocalDate> identity = new UnaryOperator<LocalDate>() {
            public LocalDate apply(LocalDate t) {
                return t;
            }
        };
        TemporalAdjuster ta = java.time.temporal.TemporalAdjusters.ofDateAdjuster(identity);
        try {
            ta.adjustInto(null);
            throw new AssertionError("Expected NullPointerException for null temporal input");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testValidAdjuster() {
        UnaryOperator<LocalDate> plusOneDay = new UnaryOperator<LocalDate>() {
            public LocalDate apply(LocalDate t) {
                return t.plusDays(1);
            }
        };
        TemporalAdjuster ta = java.time.temporal.TemporalAdjusters.ofDateAdjuster(plusOneDay);
        LocalDate date = LocalDate.of(2018, 9, 10);
        Temporal result = ta.adjustInto(date);
        if (!result.equals(date.plusDays(1))) {
            throw new AssertionError("Adjuster did not add one day as expected");
        }
    }
}
