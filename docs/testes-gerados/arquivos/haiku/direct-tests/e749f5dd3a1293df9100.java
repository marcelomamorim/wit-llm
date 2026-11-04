/*
 * @test
 * @summary Behavioral tests for TemporalAdjusters.ofDateAdjuster
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testAppliesDateFunctionToLocalDate();
        testPreservesTimeOfLocalDateTime();
        testNonDateTemporalFailsWithDateTimeException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        try {
            TemporalAdjusters.ofDateAdjuster(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null adjuster");
    }

    static void testAppliesDateFunctionToLocalDate() {
        TemporalAdjuster adjuster = TemporalAdjusters.ofDateAdjuster(d -> d.plusDays(1));
        LocalDate result = LocalDate.of(2020, 2, 28).with(adjuster);
        check(result.equals(LocalDate.of(2020, 2, 29)), "expected 2020-02-29 but got " + result);

        TemporalAdjuster identity = TemporalAdjusters.ofDateAdjuster(d -> d);
        LocalDate same = LocalDate.of(1999, 12, 31).with(identity);
        check(same.equals(LocalDate.of(1999, 12, 31)), "identity adjuster changed date to " + same);
    }

    static void testPreservesTimeOfLocalDateTime() {
        TemporalAdjuster adjuster = TemporalAdjusters.ofDateAdjuster(d -> d.withDayOfMonth(1));
        LocalDateTime input = LocalDateTime.of(2020, 1, 15, 10, 30);
        LocalDateTime result = input.with(adjuster);
        check(result.equals(LocalDateTime.of(2020, 1, 1, 10, 30)),
              "expected 2020-01-01T10:30 but got " + result);
    }

    static void testNonDateTemporalFailsWithDateTimeException() {
        TemporalAdjuster adjuster = TemporalAdjusters.ofDateAdjuster(d -> d.plusDays(1));
        try {
            LocalTime.of(10, 0).with(adjuster);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for LocalTime input");
    }
}
