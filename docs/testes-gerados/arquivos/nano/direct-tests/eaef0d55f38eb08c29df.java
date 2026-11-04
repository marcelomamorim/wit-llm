/*
 * @test
 * @summary Test LocalDateTime.minusSeconds(long) for various inputs.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusSecondsZero();
        testMinusSecondsPositive();
        testMinusSecondsNegative();
        testMinusSecondsLarge();
    }

    static void testMinusSecondsZero() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0, 0);
        LocalDateTime result = dt.minusSeconds(0);
        assert result.equals(dt) : "minusSeconds(0) should return the same datetime";
    }

    static void testMinusSecondsPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0, 30);
        LocalDateTime result = dt.minusSeconds(15);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 11, 59, 45);
        assert result.equals(expected) : "minusSeconds(15) should subtract 15 seconds";
    }

    static void testMinusSecondsNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0, 30);
        LocalDateTime result = dt.minusSeconds(-15);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0, 45);
        assert result.equals(expected) : "minusSeconds(-15) should add 15 seconds";
    }

    static void testMinusSecondsLarge() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 0, 0, 0);
        long secondsToSubtract = 60 * 60 * 24; // 1 day in seconds
        LocalDateTime result = dt.minusSeconds(secondsToSubtract);
        LocalDateTime expected = LocalDateTime.of(2023, Month.MARCH, 14, 0, 0, 0);
        assert result.equals(expected) : "minusSeconds(86400) should subtract one day";
    }
}
