/*
 * @test
 * @summary Test LocalDateTime.plusNanos method for correct addition of nanoseconds.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import static java.time.LocalDateTime.of;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusNanosPositive();
        testPlusNanosZero();
        testPlusNanosNegative();
        testPlusNanosOverflow();
    }

    static void testPlusNanosPositive() {
        LocalDateTime dateTime = of(2023, 3, 25, 10, 30, 45, 500_000_000);
        long nanosToAdd = 1_000_000_000L; // 1 second in nanoseconds
        LocalDateTime result = dateTime.plusNanos(nanosToAdd);
        LocalDateTime expected = dateTime.plusSeconds(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusNanos positive test. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusNanosZero() {
        LocalDateTime dateTime = of(2023, 3, 25, 10, 30, 45, 500_000_000);
        long nanosToAdd = 0L;
        LocalDateTime result = dateTime.plusNanos(nanosToAdd);
        if (!result.equals(dateTime)) {
            throw new AssertionError("Failed: plusNanos zero test. Expected: " + dateTime + ", but got: " + result);
        }
    }

    static void testPlusNanosNegative() {
        LocalDateTime dateTime = of(2023, 3, 25, 10, 30, 45, 500_000_000);
        long nanosToAdd = -1_000_000_000L; // -1 second in nanoseconds
        LocalDateTime result = dateTime.plusNanos(nanosToAdd);
        LocalDateTime expected = dateTime.minusSeconds(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusNanos negative test. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusNanosOverflow() {
        LocalDateTime dateTime = LocalDateTime.of(9999, 12, 31, 23, 59, 59, 999_999_999);
        long nanosToAdd = 1L; // adding 1 nanosecond should overflow to next day
        LocalDateTime result = dateTime.plusNanos(nanosToAdd);
        LocalDateTime expected = LocalDateTime.of(10000, 1, 1, 0, 0, 0, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusNanos overflow test. Expected: " + expected + ", but got: " + result);
        }
    }
}
