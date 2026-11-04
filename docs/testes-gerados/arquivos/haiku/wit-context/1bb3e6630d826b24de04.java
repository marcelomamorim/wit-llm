/*
 * @test
 * @summary Witup-generated behavioral checks for HijrahDate.toEpochDay()
 * @run main HijrahDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testConsecutiveDaysWithinMonth();
        testRoundTripFromLocalDate();
        testKnownGregorianCorrespondence();
    }

    static void testConsecutiveDaysWithinMonth() {
        for (int month = 1; month <= 12; month++) {
            for (int day = 1; day <= 28; day++) {
                long current = HijrahDate.of(1433, month, day).toEpochDay();
                long next = HijrahDate.of(1433, month, day + 1).toEpochDay();
                check(next - current == 1,
                        "consecutive days must differ by 1 at month " + month + " day " + day);
            }
        }
    }

    static void testRoundTripFromLocalDate() {
        long start = LocalDate.of(2000, 1, 1).toEpochDay();
        for (long epochDay = start; epochDay < start + 3650; epochDay += 37) {
            LocalDate gregorian = LocalDate.ofEpochDay(epochDay);
            long actual = HijrahDate.from(gregorian).toEpochDay();
            check(actual == epochDay, "round trip failed for epoch day " + epochDay);
        }
    }

    static void testKnownGregorianCorrespondence() {
        LocalDate gregorian = LocalDate.of(2000, 1, 1);
        long expected = gregorian.toEpochDay();
        long actual = HijrahDate.from(gregorian).toEpochDay();
        check(actual == expected, "toEpochDay must match the underlying epoch day of the same date");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
