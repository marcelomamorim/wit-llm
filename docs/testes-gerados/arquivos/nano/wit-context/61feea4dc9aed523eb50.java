/*
 * @test
 * @summary Tests for OffsetTime.toEpochSecond(LocalDate)
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testToEpochSecondWithNull();
        testToEpochSecondWithValidDate();
    }

    public static void testToEpochSecondWithNull() {
        OffsetTime offsetTime = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);
        try {
            offsetTime.toEpochSecond(null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException thrown as expected");
        }
    }

    public static void testToEpochSecondWithValidDate() {
        OffsetTime offsetTime = OffsetTime.of(12, 0, 0, 0, ZoneOffset.ofHours(2));
        LocalDate date = LocalDate.of(2023, 1, 1);
        long epochSeconds = offsetTime.toEpochSecond(date);
        // Compute expected value manually
        long epochDay = date.toEpochDay();
        long expectedSecs = epochDay * 86400 + offsetTime.toSecondOfDay() - offsetTime.getOffset().getTotalSeconds();
        if (epochSeconds != expectedSecs) {
            System.err.println("Mismatch: expected " + expectedSecs + " but got " + epochSeconds);
        } else {
            System.out.println("toEpochSecond returns expected value");
        }
    }
}
