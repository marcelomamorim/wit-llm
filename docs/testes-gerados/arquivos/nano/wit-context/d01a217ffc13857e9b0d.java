/*
 * @test
 * @summary Tests for java.time.ZonedDateTime.of(LocalDate, LocalTime, ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullLocalDate();
        testNullZone();
    }

    static void testValidInputs() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalTime time = LocalTime.of(12, 30, 45);
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);
        if (zdt == null) {
            throw new AssertionError("Expected non-null ZonedDateTime");
        }
    }

    static void testNullLocalDate() {
        try {
            LocalDate date = null;
            LocalTime time = LocalTime.of(12, 0);
            ZoneId zone = ZoneId.of("UTC");
            ZonedDateTime.of(date, time, zone);
            throw new AssertionError("Expected NullPointerException for null LocalDate");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testNullZone() {
        try {
            LocalDate date = LocalDate.of(2023, 3, 15);
            LocalTime time = LocalTime.of(12, 0);
            ZoneId zone = null;
            ZonedDateTime.of(date, time, zone);
            throw new AssertionError("Expected NullPointerException for null ZoneId");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
