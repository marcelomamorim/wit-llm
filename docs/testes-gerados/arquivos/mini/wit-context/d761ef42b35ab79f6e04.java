/*
 * @test
 * @summary Test java.time.ZonedDateTime.of(LocalDateTime, ZoneId) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.DateTimeException;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testValidZonedDateTimeCreation();
        testNullLocalDateTime();
        testNullZoneId();
    }

    private static void testValidZonedDateTimeCreation() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 1, 12, 30, 45, 123456789);
        ZoneId zone = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        if (!zdt.toLocalDateTime().equals(ldt)) {
            throw new AssertionError("LocalDateTime mismatch");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZoneId mismatch");
        }
    }

    private static void testNullLocalDateTime() {
        ZoneId zone = ZoneId.of("UTC");
        try {
            ZonedDateTime.of(null, zone);
            throw new AssertionError("Expected NullPointerException for null LocalDateTime");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullZoneId() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 1, 12, 30);
        try {
            ZonedDateTime.of(ldt, null);
            throw new AssertionError("Expected NullPointerException for null ZoneId");
        } catch (NullPointerException expected) {
        }
    }
}
