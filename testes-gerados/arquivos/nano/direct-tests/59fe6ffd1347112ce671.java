/*
 * @test
 * @summary Test OffsetTime.ofInstant method for various instants and zones
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testOfInstantWithUTC();
        testOfInstantWithSpecificZone();
        testOfInstantWithNegativeOffset();
        testOfInstantWithLargeEpochSeconds();
    }

    static void testOfInstantWithUTC() {
        Instant instant = Instant.ofEpochSecond(0, 0); // epoch
        ZoneId zone = ZoneOffset.UTC;
        OffsetTime result = OffsetTime.ofInstant(instant, zone);
        assert result != null : "Result should not be null";
        assert result.getOffset().equals(ZoneOffset.UTC) : "Offset should be UTC";
        // Time should be 00:00:00.000000000
        assert result.toLocalTime().equals(java.time.LocalTime.of(0, 0, 0, 0)) : "Time should be midnight";
    }

    static void testOfInstantWithSpecificZone() {
        Instant instant = Instant.ofEpochSecond(1609459200); // 2021-01-01T00:00:00Z
        ZoneId zone = ZoneId.of("America/New_York");
        OffsetTime result = OffsetTime.ofInstant(instant, zone);
        assert result != null : "Result should not be null";
        assert result.getOffset().equals(zone.getRules().getOffset(instant)) : "Offset should match zone rules";
        // Time should correspond to 2021-01-01T00:00:00Z with offset
        java.time.LocalTime expectedTime = java.time.LocalTime.of(19, 0); // EST offset -05:00
        assert result.toLocalTime().equals(expectedTime) : "Time should be 19:00";
    }

    static void testOfInstantWithNegativeOffset() {
        Instant instant = Instant.ofEpochSecond(0); // epoch
        ZoneOffset offset = ZoneOffset.ofHours(-3);
        OffsetTime result = OffsetTime.ofInstant(instant, ZoneId.ofOffset("UTC-3", offset));
        assert result != null : "Result should not be null";
        assert result.getOffset().equals(offset) : "Offset should be -03:00";
        // Time should be 03:00:00
        assert result.toLocalTime().equals(java.time.LocalTime.of(3, 0)) : "Time should be 03:00";
    }

    static void testOfInstantWithLargeEpochSeconds() {
        long largeEpoch = 9223372036854775807L; // Long.MAX_VALUE
        Instant instant = Instant.ofEpochSecond(largeEpoch, 999999999);
        ZoneOffset offset = ZoneOffset.ofHours(0);
        OffsetTime result = OffsetTime.ofInstant(instant, ZoneId.ofOffset("UTC", offset));
        assert result != null : "Result should not be null";
        // The time should be computed without exception
        assert result.toLocalTime() != null : "LocalTime should not be null";
    }
}
