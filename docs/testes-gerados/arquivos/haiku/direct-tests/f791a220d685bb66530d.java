/*
 * @test
 * @summary Verifies TemporalAdjusters.firstInMonth returns the first occurrence of the given day of week in the month
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        // First Monday of May 2024 (May 1 is a Wednesday) -> May 6
        check(LocalDate.of(2024, 5, 20).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)),
              LocalDate.of(2024, 5, 6));

        // May 1, 2024 is a Wednesday, so the first Wednesday is the 1st itself
        check(LocalDate.of(2024, 5, 15).with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)),
              LocalDate.of(2024, 5, 1));

        // Feb 1, 2024 is a Thursday; first Sunday is Feb 4
        check(LocalDate.of(2024, 2, 28).with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY)),
              LocalDate.of(2024, 2, 4));

        // Jan 1, 2024 is a Monday; first Monday is Jan 1
        check(LocalDate.of(2024, 1, 15).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)),
              LocalDate.of(2024, 1, 1));

        // Result must be within the same month as the input date
        LocalDate adjusted = LocalDate.of(2023, 12, 31)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        check(adjusted, LocalDate.of(2023, 12, 1));

        // Null day of week must be rejected at adjuster creation
        try {
            TemporalAdjusters.firstInMonth(null);
            throw new RuntimeException("expected NullPointerException for null dayOfWeek");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(LocalDate actual, LocalDate expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("mismatch: expected " + expected + " but was " + actual);
        }
    }
}
