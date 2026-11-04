/*
 * @test
 * @summary Test OffsetDateTime.of(LocalDate, LocalTime, ZoneOffset) factory method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testOfBasic();
        testOfMidnightAndOffsetZero();
        testOfEndOfDayAndNegativeOffset();
    }

    private static void testOfBasic() {
        LocalDate date = LocalDate.of(2018, 9, 25);
        LocalTime time = LocalTime.of(13, 45, 30, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
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

    private static void testOfMidnightAndOffsetZero() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        LocalTime time = LocalTime.MIDNIGHT;
        ZoneOffset offset = ZoneOffset.UTC;
        OffsetDateTime odt = OffsetDateTime.of(date, time, offset);
        if (!odt.toLocalDate().equals(date)) {
            throw new AssertionError("Date mismatch at midnight");
        }
        if (!odt.toLocalTime().equals(time)) {
            throw new AssertionError("Time mismatch at midnight");
        }
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("Offset mismatch at midnight");
        }
    }

    private static void testOfEndOfDayAndNegativeOffset() {
        LocalDate date = LocalDate.of(1999, 12, 31);
        LocalTime time = LocalTime.of(23, 59, 59, 999999999);
        ZoneOffset offset = ZoneOffset.ofHours(-5);
        OffsetDateTime odt = OffsetDateTime.of(date, time, offset);
        if (!odt.toLocalDate().equals(date)) {
            throw new AssertionError("Date mismatch at end of day");
        }
        if (!odt.toLocalTime().equals(time)) {
            throw new AssertionError("Time mismatch at end of day");
        }
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("Offset mismatch at end of day");
        }
    }
}
