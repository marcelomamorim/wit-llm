/*
 * @test
 * @summary Test for LocalDateTime.minusNanos(long nanos)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import static java.time.LocalDateTime.of;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusNanosPositive();
        testMinusNanosZero();
        testMinusNanosLarge();
        testMinusNanosNegative();
    }

    static void testMinusNanosPositive() {
        LocalDateTime dateTime = of(2023, 3, 15, 12, 0);
        long nanosToSubtract = 1_000_000_000L; // 1 second in nanos
        LocalDateTime result = dateTime.minusNanos(nanosToSubtract);
        LocalDateTime expected = dateTime.plusSeconds(-1);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusNanos positive case. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testMinusNanosZero() {
        LocalDateTime dateTime = of(2023, 3, 15, 12, 0);
        LocalDateTime result = dateTime.minusNanos(0);
        if (!result.equals(dateTime)) {
            throw new AssertionError("Failed: minusNanos zero case. Expected: " + dateTime + ", but got: " + result);
        }
    }

    static void testMinusNanosLarge() {
        LocalDateTime dateTime = of(2023, 3, 15, 12, 0);
        long nanosToSubtract = 3L * 365 * 24 * 60 * 60 * 1_000_000_000L; // 3 years in nanos (approximate)
        LocalDateTime result = dateTime.minusNanos(nanosToSubtract);
        LocalDateTime expected = dateTime.plusSeconds(-3 * 365 * 24 * 60 * 60);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusNanos large case. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testMinusNanosNegative() {
        LocalDateTime dateTime = of(2023, 3, 15, 12, 0);
        long nanosToAdd = -500_000_000L; // subtract 0.5 seconds
        LocalDateTime result = dateTime.minusNanos(nanosToAdd);
        LocalDateTime expected = dateTime.plusSeconds(0).plusNanos(500_000_000L);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusNanos negative case. Expected: " + expected + ", but got: " + result);
        }
    }
}
