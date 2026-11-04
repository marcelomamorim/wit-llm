/*
 * @test
 * @summary Witup-generated checks for TemporalAdjusters.ofDateAdjuster null handling and observable adjustment behavior
 * @run main TemporalAdjustersWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.UnaryOperator;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testNullAdjusterThrowsNpe();
        testNullTemporalThrowsNpe();
        testAdjusterAppliedToLocalDate();
        testAdjusterReceivesInputDate();
    }

    static void testNullAdjusterThrowsNpe() {
        try {
            TemporalAdjusters.ofDateAdjuster(null);
            throw new AssertionError("Expected NullPointerException for null dateBasedAdjuster");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullTemporalThrowsNpe() {
        UnaryOperator<LocalDate> op = d -> d.plusDays(1);
        TemporalAdjuster adj = TemporalAdjusters.ofDateAdjuster(op);
        try {
            adj.adjustInto(null);
            throw new AssertionError("Expected NullPointerException for null temporal");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testAdjusterAppliedToLocalDate() {
        UnaryOperator<LocalDate> op = d -> d.plusDays(3);
        TemporalAdjuster adj = TemporalAdjusters.ofDateAdjuster(op);
        LocalDate in = LocalDate.of(2020, 1, 10);
        LocalDate out = in.with(adj);
        check(LocalDate.of(2020, 1, 13).equals(out), "plusDays(3) result was " + out);
    }

    static void testAdjusterReceivesInputDate() {
        UnaryOperator<LocalDate> op = d -> d.withDayOfMonth(1);
        TemporalAdjuster adj = TemporalAdjusters.ofDateAdjuster(op);
        LocalDate in = LocalDate.of(2020, 2, 15);
        LocalDate out = in.with(adj);
        check(LocalDate.of(2020, 2, 1).equals(out), "withDayOfMonth(1) result was " + out);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
