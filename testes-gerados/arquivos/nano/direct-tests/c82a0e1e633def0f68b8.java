/*
 * @test
 * @summary Test LocalDateTime.minusMinutes(long) for correct behavior.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testMinusMinutesPositive();
        testMinusMinutesZero();
        testMinusMinutesLarge();
        testMinusMinutesNegative();
    }

    static void testMinusMinutesPositive() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 0);
        LocalDateTime result = dateTime.minusMinutes(30);
        LocalDateTime expected = LocalDateTime.of(2023, 3, 15, 11, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusMinutes(30)");
        }
    }

    static void testMinusMinutesZero() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 0);
        LocalDateTime result = dateTime.minusMinutes(0);
        if (!result.equals(dateTime)) {
            throw new AssertionError("Failed: minusMinutes(0)");
        }
    }

    static void testMinusMinutesLarge() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 0);
        LocalDateTime result = dateTime.minusMinutes(1440); // 1 day
        LocalDateTime expected = LocalDateTime.of(2023, 3, 14, 12, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusMinutes(1440)");
        }
    }

    static void testMinusMinutesNegative() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 0);
        LocalDateTime result = dateTime.minusMinutes(-30); // should add 30 minutes
        LocalDateTime expected = LocalDateTime.of(2023, 3, 15, 12, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusMinutes(-30)");
        }
    }
}
