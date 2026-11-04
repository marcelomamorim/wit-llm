/*
 * @test
 * @summary Test ZonedDateTime.of(LocalDateTime, ZoneId) method for correctness.
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethodWithValidInputs();
        testOfMethodWithNullLocalDateTime();
        testOfMethodWithNullZoneId();
    }

    static void testOfMethodWithValidInputs() {
        LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        assert zdt != null : "ZonedDateTime should not be null";
        assert zdt.getYear() == 2023 : "Year should be 2023";
        assert zdt.getMonthValue() == 3 : "Month should be 3";
        assert zdt.getDayOfMonth() == 15 : "Day should be 15";
        assert zdt.getHour() == 10 : "Hour should be 10";
        assert zdt.getMinute() == 30 : "Minute should be 30";
        assert zdt.getSecond() == 45 : "Second should be 45";
        assert zdt.getZone().equals(zone) : "Zone should match input zone";
    }

    static void testOfMethodWithNullLocalDateTime() {
        try {
            ZoneId zone = ZoneId.of("UTC");
            ZonedDateTime.of(null, zone);
            assert false : "Expected NullPointerException for null LocalDateTime";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfMethodWithNullZoneId() {
        try {
            LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
            ZonedDateTime.of(ldt, null);
            assert false : "Expected NullPointerException for null ZoneId";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
