/*
 * @test
 * @summary Test java.time.LocalTime.ofInstant(Instant, ZoneId) for null checks and basic correctness
 * @run main LocalTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testBasicConversion();
    }

    private static void testNullInstant() {
        try {
            LocalTime.ofInstant(null, ZoneOffset.UTC);
            throw new RuntimeException("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullZone() {
        try {
            LocalTime.ofInstant(Instant.EPOCH, null);
            throw new RuntimeException("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testBasicConversion() {
        Instant instant = Instant.ofEpochSecond(0, 0); // 1970-01-01T00:00:00Z
        ZoneOffset zone = ZoneOffset.UTC;
        LocalTime time = LocalTime.ofInstant(instant, zone);
        if (!time.equals(LocalTime.MIDNIGHT)) {
            throw new RuntimeException("Expected MIDNIGHT for epoch instant at UTC, got: " + time);
        }

        // Test with offset +01:00, instant at 00:00 UTC should be 01:00 local time
        ZoneOffset offsetPlusOne = ZoneOffset.ofHours(1);
        LocalTime timePlusOne = LocalTime.ofInstant(instant, offsetPlusOne);
        if (!timePlusOne.equals(LocalTime.of(1, 0))) {
            throw new RuntimeException("Expected 01:00 for epoch instant at +01:00, got: " + timePlusOne);
        }

        // Test with offset -01:00, instant at 00:00 UTC should be 23:00 previous day local time
        ZoneOffset offsetMinusOne = ZoneOffset.ofHours(-1);
        LocalTime timeMinusOne = LocalTime.ofInstant(instant, offsetMinusOne);
        if (!timeMinusOne.equals(LocalTime.of(23, 0))) {
            throw new RuntimeException("Expected 23:00 for epoch instant at -01:00, got: " + timeMinusOne);
        }
    }
}
