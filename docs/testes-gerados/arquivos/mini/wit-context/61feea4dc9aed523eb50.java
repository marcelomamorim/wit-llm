/*
 * @test
 * @summary Test OffsetTime.toEpochSecond(LocalDate) method behavior including null input
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testToEpochSecondWithValidDate();
        testToEpochSecondWithNullDate();
        System.out.println("All tests passed.");
    }

    private static void testToEpochSecondWithValidDate() {
        // Arrange
        LocalTime localTime = LocalTime.of(12, 0, 0); // noon
        ZoneOffset offset = ZoneOffset.ofHours(2); // +02:00
        OffsetTime offsetTime = OffsetTime.of(localTime, offset);
        LocalDate date = LocalDate.of(1970, 1, 2); // epoch day 1

        // Act
        long epochSecond = offsetTime.toEpochSecond(date);

        // Expected calculation:
        // epochDay = 1
        // secs = 1 * 86400 + 12*3600 = 86400 + 43200 = 129600
        // secs -= offset total seconds (2*3600=7200) => 129600 - 7200 = 122400

        long expected = 122400L;

        if (epochSecond != expected) {
            throw new AssertionError("Expected " + expected + " but got " + epochSecond);
        }
    }

    private static void testToEpochSecondWithNullDate() {
        LocalTime localTime = LocalTime.of(0, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        OffsetTime offsetTime = OffsetTime.of(localTime, offset);

        try {
            offsetTime.toEpochSecond(null);
            throw new AssertionError("Expected NullPointerException for null date argument");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"date".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
