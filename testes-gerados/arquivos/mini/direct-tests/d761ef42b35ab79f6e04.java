/*
 * @test
 * @summary Test java.time.ZonedDateTime.of(LocalDateTime, ZoneId) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testOfWithValidLocalDateTimeAndZoneId();
        testOfWithNullLocalDateTime();
        testOfWithNullZoneId();
    }

    private static void testOfWithValidLocalDateTimeAndZoneId() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 25, 15, 30, 45, 123456789);
        ZoneId zone = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        if (!zdt.toLocalDateTime().equals(ldt)) {
            throw new AssertionError("LocalDateTime mismatch");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZoneId mismatch");
        }
    }

    private static void testOfWithNullLocalDateTime() {
        ZoneId zone = ZoneId.of("UTC");
        try {
            ZonedDateTime.of(null, zone);
            throw new AssertionError("Expected NullPointerException for null LocalDateTime");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfWithNullZoneId() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 25, 15, 30);
        try {
            ZonedDateTime.of(ldt, null);
            throw new AssertionError("Expected NullPointerException for null ZoneId");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
