/*
 * @test
 * @summary Test java.time.OffsetTime.toEpochSecond(LocalDate) method
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testToEpochSecondBasic();
        testToEpochSecondWithOffset();
        testToEpochSecondNullDate();
    }

    private static void testToEpochSecondBasic() {
        LocalDate date = LocalDate.of(1970, 1, 1);
        OffsetTime time = OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC);
        long epochSecond = time.toEpochSecond(date);
        if (epochSecond != 0L) {
            throw new AssertionError("Expected 0 epoch second for 1970-01-01T00:00Z but got " + epochSecond);
        }
    }

    private static void testToEpochSecondWithOffset() {
        LocalDate date = LocalDate.of(1970, 1, 2); // one day after epoch
        OffsetTime time = OffsetTime.of(1, 0, 0, 0, ZoneOffset.ofHours(1)); // 01:00 +01:00
        // Expected epoch second = (1 day * 86400) + 3600 (time) - 3600 (offset) = 86400
        long epochSecond = time.toEpochSecond(date);
        if (epochSecond != 86400L) {
            throw new AssertionError("Expected 86400 epoch second for 1970-01-02T01:00+01:00 but got " + epochSecond);
        }
    }

    private static void testToEpochSecondNullDate() {
        OffsetTime time = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);
        try {
            time.toEpochSecond(null);
            throw new AssertionError("Expected NullPointerException when passing null date");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
