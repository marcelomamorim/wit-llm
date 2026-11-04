/*
 * @test
 * @summary Test LocalDateTime.plusSeconds() method for various inputs.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusSecondsZero();
        testPlusSecondsPositive();
        testPlusSecondsNegative();
        testPlusSecondsLarge();
    }

    static void testPlusSecondsZero() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30, 45);
        LocalDateTime result = dt.plusSeconds(0);
        assert result.equals(dt) : "plusSeconds(0) should return the same LocalDateTime";
    }

    static void testPlusSecondsPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30, 45);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 10, 31, 0);
        LocalDateTime result = dt.plusSeconds(15);
        assert result.equals(expected) : "plusSeconds(15) did not produce expected result";
    }

    static void testPlusSecondsNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30, 45);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30, 30);
        LocalDateTime result = dt.plusSeconds(-15);
        assert result.equals(expected) : "plusSeconds(-15) did not produce expected result";
    }

    static void testPlusSecondsLarge() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30, 45);
        long largeSeconds = 1000000000L;
        LocalDateTime result = dt.plusSeconds(largeSeconds);
        // Expected date calculation: adding largeSeconds to dt
        LocalDateTime expected = dt.plusDays(largeSeconds / 86400).plusSeconds(largeSeconds % 86400);
        assert result.equals(expected) : "plusSeconds(largeSeconds) did not produce expected result";
    }
}
