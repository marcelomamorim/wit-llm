/*
 * @test
 * @summary Tests the static method ZonedDateTime.of(LocalDate, LocalTime, ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethodWithValidInputs();
        testOfMethodWithNullDate();
        testOfMethodWithNullTime();
        testOfMethodWithNullZone();
    }

    static void testOfMethodWithValidInputs() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalTime time = LocalTime.of(12, 30, 45);
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);
        assert zdt != null : "ZonedDateTime should not be null";
        assert zdt.getYear() == 2023 : "Year should be 2023";
        assert zdt.getMonthValue() == 3 : "Month should be 3";
        assert zdt.getDayOfMonth() == 15 : "Day should be 15";
        assert zdt.getHour() == 12 : "Hour should be 12";
        assert zdt.getMinute() == 30 : "Minute should be 30";
        assert zdt.getSecond() == 45 : "Second should be 45";
        assert zdt.getZone().equals(zone) : "Zone should match";
    }

    static void testOfMethodWithNullDate() {
        try {
            LocalTime time = LocalTime.of(10, 0);
            ZoneId zone = ZoneId.of("UTC");
            ZonedDateTime.of(null, time, zone);
            assert false : "Expected NullPointerException for null date";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfMethodWithNullTime() {
        try {
            LocalDate date = LocalDate.of(2023, 1, 1);
            ZoneId zone = ZoneId.of("UTC");
            ZonedDateTime.of(date, null, zone);
            assert false : "Expected NullPointerException for null time";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfMethodWithNullZone() {
        try {
            LocalDate date = LocalDate.of(2023, 1, 1);
            LocalTime time = LocalTime.of(0, 0);
            ZonedDateTime.of(date, time, null);
            assert false : "Expected NullPointerException for null zone";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
