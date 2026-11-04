/*
 * @test
 * @summary Test LocalDateTime.ofInstant method for various instants and zones
 * @run main LocalDateTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfInstantWithUTC();
        testOfInstantWithSpecificZone();
        testOfInstantWithNullInstant();
        testOfInstantWithNullZone();
    }

    static void testOfInstantWithUTC() {
        Instant instant = Instant.ofEpochSecond(1609459200, 123456789); // 2021-01-01T00:00:00.123456789Z
        ZoneId zone = ZoneId.of("UTC");
        LocalDateTime result = LocalDateTime.ofInstant(instant, zone);
        assert result != null : "Result should not be null";
        // Expected date: 2021-01-01T00:00:00.123456789
        assert result.getYear() == 2021 : "Year mismatch";
        assert result.getMonthValue() == 1 : "Month mismatch";
        assert result.getDayOfMonth() == 1 : "Day mismatch";
        assert result.getHour() == 0 : "Hour mismatch";
        assert result.getMinute() == 0 : "Minute mismatch";
        assert result.getSecond() == 0 : "Second mismatch";
        assert result.getNano() == 123456789 : "Nano mismatch";
    }

    static void testOfInstantWithSpecificZone() {
        Instant instant = Instant.ofEpochSecond(1609459200, 987654321); // 2021-01-01T00:00:00.987654321Z
        ZoneId zone = ZoneId.of("America/New_York");
        LocalDateTime result = LocalDateTime.ofInstant(instant, zone);
        assert result != null : "Result should not be null";
        // The expected local date depends on the zone offset at that instant
        // For EST (UTC-5), the local time should be 2020-12-31T19:00:00.987654321
        assert result.getYear() == 2020 : "Year mismatch";
        assert result.getMonthValue() == 12 : "Month mismatch";
        assert result.getDayOfMonth() == 31 : "Day mismatch";
        assert result.getHour() == 19 : "Hour mismatch";
        assert result.getMinute() == 0 : "Minute mismatch";
        assert result.getSecond() == 0 : "Second mismatch";
        assert result.getNano() == 987654321 : "Nano mismatch";
    }

    static void testOfInstantWithNullInstant() {
        try {
            LocalDateTime.ofInstant(null, ZoneId.of("UTC"));
            assert false : "Expected NullPointerException for null instant";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfInstantWithNullZone() {
        Instant instant = Instant.ofEpochSecond(0);
        try {
            LocalDateTime.ofInstant(instant, null);
            assert false : "Expected NullPointerException for null zone";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
