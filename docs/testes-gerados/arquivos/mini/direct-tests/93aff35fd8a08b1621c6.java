/*
 * @test
 * @summary Test java.time.LocalDate.toEpochSecond(LocalTime, ZoneOffset) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testToEpochSecondBasic();
        testToEpochSecondEpochDayZero();
        testToEpochSecondWithOffset();
        testToEpochSecondNullTime();
        testToEpochSecondNullOffset();
    }

    private static void testToEpochSecondBasic() {
        LocalDate date = LocalDate.of(1970, 1, 2); // epoch day 1
        LocalTime time = LocalTime.of(0, 0, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        long result = date.toEpochSecond(time, offset);
        // 1 day after epoch * seconds per day = 86400
        if (result != 86400L) {
            throw new AssertionError("Expected 86400 but got " + result);
        }
    }

    private static void testToEpochSecondEpochDayZero() {
        LocalDate date = LocalDate.of(1970, 1, 1); // epoch day 0
        LocalTime time = LocalTime.of(0, 0, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        long result = date.toEpochSecond(time, offset);
        if (result != 0L) {
            throw new AssertionError("Expected 0 but got " + result);
        }
    }

    private static void testToEpochSecondWithOffset() {
        LocalDate date = LocalDate.of(1970, 1, 2); // epoch day 1
        LocalTime time = LocalTime.of(0, 0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(1); // offset +1 hour
        long result = date.toEpochSecond(time, offset);
        // 86400 - 3600 = 82800
        if (result != 82800L) {
            throw new AssertionError("Expected 82800 but got " + result);
        }
    }

    private static void testToEpochSecondNullTime() {
        LocalDate date = LocalDate.of(1970, 1, 1);
        ZoneOffset offset = ZoneOffset.UTC;
        try {
            date.toEpochSecond(null, offset);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException e) {
            if (!"time".equals(e.getMessage())) {
                throw new AssertionError("Expected message 'time' but got: " + e.getMessage());
            }
        }
    }

    private static void testToEpochSecondNullOffset() {
        LocalDate date = LocalDate.of(1970, 1, 1);
        LocalTime time = LocalTime.of(0, 0, 0);
        try {
            date.toEpochSecond(time, null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            if (!"offset".equals(e.getMessage())) {
                throw new AssertionError("Expected message 'offset' but got: " + e.getMessage());
            }
        }
    }
}
