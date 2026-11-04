/*
 * @test
 * @summary Test OffsetDateTime.of(LocalDate, LocalTime, ZoneOffset) for null argument handling and normal creation
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testNullDate();
        testNullTime();
        testValidCreation();
    }

    private static void testNullDate() {
        LocalTime time = LocalTime.of(12, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        try {
            OffsetDateTime.of(null, time, offset);
            throw new AssertionError("Expected NullPointerException for null date");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullTime() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        ZoneOffset offset = ZoneOffset.UTC;
        try {
            OffsetDateTime.of(date, null, offset);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidCreation() {
        LocalDate date = LocalDate.of(2020, 2, 29);
        LocalTime time = LocalTime.of(23, 59, 59, 999999999);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(-5, -30);
        OffsetDateTime odt = OffsetDateTime.of(date, time, offset);
        if (!odt.toLocalDate().equals(date)) {
            throw new AssertionError("Date mismatch");
        }
        if (!odt.toLocalTime().equals(time)) {
            throw new AssertionError("Time mismatch");
        }
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("Offset mismatch");
        }
    }
}
