/*
 * @test
 * @summary Test java.time.LocalTime.ofInstant(Instant, ZoneId) method
 * @run main LocalTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.DateTimeException;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testBasicConversion();
        testEpochStartUTC();
        testNegativeEpochSecondWithOffset();
        testNullInstant();
        testNullZone();
    }

    private static void testBasicConversion() {
        Instant instant = Instant.ofEpochSecond(3600, 123456789); // 01:00:00.123456789 UTC
        ZoneId zone = ZoneOffset.UTC;
        LocalTime time = LocalTime.ofInstant(instant, zone);
        if (time.getHour() != 1 || time.getMinute() != 0 || time.getSecond() != 0 || time.getNano() != 123456789) {
            throw new AssertionError("Basic conversion failed: " + time);
        }
    }

    private static void testEpochStartUTC() {
        Instant instant = Instant.ofEpochSecond(0, 0);
        ZoneId zone = ZoneOffset.UTC;
        LocalTime time = LocalTime.ofInstant(instant, zone);
        if (!time.equals(LocalTime.MIDNIGHT)) {
            throw new AssertionError("Epoch start UTC should be midnight: " + time);
        }
    }

    private static void testNegativeEpochSecondWithOffset() {
        // Instant before epoch, offset +01:00, local time should be adjusted
        Instant instant = Instant.ofEpochSecond(-1, 0); // 1969-12-31T23:59:59Z
        ZoneOffset offset = ZoneOffset.ofHours(1); // +01:00
        LocalTime time = LocalTime.ofInstant(instant, offset);
        // localSecond = -1 + 3600 = 3599 seconds = 00:59:59 local time
        if (time.getHour() != 0 || time.getMinute() != 59 || time.getSecond() != 59) {
            throw new AssertionError("Negative epoch second with offset failed: " + time);
        }
    }

    private static void testNullInstant() {
        try {
            LocalTime.ofInstant(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            if (!"instant".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for instant: " + e.getMessage());
            }
        }
    }

    private static void testNullZone() {
        try {
            LocalTime.ofInstant(Instant.EPOCH, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            if (!"zone".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for zone: " + e.getMessage());
            }
        }
    }
}
