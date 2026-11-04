/*
 * @test
 * @summary Test ZonedDateTime.of(LocalDate, LocalTime, ZoneId) basic behavior
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        // Test with typical values
        LocalDate date = LocalDate.of(2018, 9, 25);
        LocalTime time = LocalTime.of(13, 45, 30, 123456789);
        ZoneId zone = ZoneId.of("Europe/London");

        ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);

        if (!zdt.toLocalDate().equals(date)) {
            throw new AssertionError("LocalDate mismatch");
        }
        if (!zdt.toLocalTime().equals(time)) {
            throw new AssertionError("LocalTime mismatch");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZoneId mismatch");
        }

        // Test with minimum values
        LocalDate minDate = LocalDate.of(1, 1, 1);
        LocalTime minTime = LocalTime.of(0, 0);
        ZoneId zoneUTC = ZoneId.of("UTC");

        ZonedDateTime zdtMin = ZonedDateTime.of(minDate, minTime, zoneUTC);

        if (!zdtMin.toLocalDate().equals(minDate)) {
            throw new AssertionError("Min LocalDate mismatch");
        }
        if (!zdtMin.toLocalTime().equals(minTime)) {
            throw new AssertionError("Min LocalTime mismatch");
        }
        if (!zdtMin.getZone().equals(zoneUTC)) {
            throw new AssertionError("Min ZoneId mismatch");
        }

        // Test with maximum values
        LocalDate maxDate = LocalDate.of(9999, 12, 31);
        LocalTime maxTime = LocalTime.of(23, 59, 59, 999999999);

        ZonedDateTime zdtMax = ZonedDateTime.of(maxDate, maxTime, zoneUTC);

        if (!zdtMax.toLocalDate().equals(maxDate)) {
            throw new AssertionError("Max LocalDate mismatch");
        }
        if (!zdtMax.toLocalTime().equals(maxTime)) {
            throw new AssertionError("Max LocalTime mismatch");
        }
        if (!zdtMax.getZone().equals(zoneUTC)) {
            throw new AssertionError("Max ZoneId mismatch");
        }

        // Test with zone offset instead of zone region
        ZoneId offsetZone = ZoneId.of("+02:00");
        ZonedDateTime zdtOffset = ZonedDateTime.of(date, time, offsetZone);

        if (!zdtOffset.getZone().equals(offsetZone)) {
            throw new AssertionError("Offset ZoneId mismatch");
        }

        System.out.println("All tests passed.");
    }
}
