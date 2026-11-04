/*
 * @test
 * @summary Verifies OffsetTime.atDate(LocalDate) combines date, time and offset into an OffsetDateTime
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testBasicCombination();
        testNanosAndOffsetPreserved();
        testNegativeOffset();
        testDateIsUsedAsGiven();
        testNullDateThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicCombination() {
        OffsetTime ot = OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(2));
        LocalDate date = LocalDate.of(2020, 1, 31);
        OffsetDateTime odt = ot.atDate(date);
        check(odt.toLocalDate().equals(date), "date mismatch");
        check(odt.toLocalTime().equals(LocalTime.of(10, 15, 30)), "time mismatch");
        check(odt.getOffset().equals(ZoneOffset.ofHours(2)), "offset mismatch");
        OffsetDateTime expected = OffsetDateTime.of(date, LocalTime.of(10, 15, 30), ZoneOffset.ofHours(2));
        check(odt.equals(expected), "result not equal to OffsetDateTime.of(date, time, offset)");
    }

    static void testNanosAndOffsetPreserved() {
        LocalTime time = LocalTime.of(23, 59, 59, 999_999_999);
        ZoneOffset offset = ZoneOffset.ofHoursMinutesSeconds(5, 30, 15);
        OffsetTime ot = OffsetTime.of(time, offset);
        OffsetDateTime odt = ot.atDate(LocalDate.of(2000, 2, 29));
        check(odt.toLocalTime().getNano() == 999_999_999, "nanos not preserved");
        check(odt.getOffset().getTotalSeconds() == offset.getTotalSeconds(), "offset seconds not preserved");
        check(odt.toLocalDate().equals(LocalDate.of(2000, 2, 29)), "leap day date mismatch");
    }

    static void testNegativeOffset() {
        OffsetTime ot = OffsetTime.of(0, 0, 0, 0, ZoneOffset.ofHoursMinutes(-5, -30));
        OffsetDateTime odt = ot.atDate(LocalDate.of(1999, 12, 31));
        check(odt.getOffset().equals(ZoneOffset.ofHoursMinutes(-5, -30)), "negative offset not preserved");
        check(odt.toLocalTime().equals(LocalTime.MIDNIGHT), "midnight time mismatch");
        check(odt.toLocalDate().equals(LocalDate.of(1999, 12, 31)), "date mismatch for negative offset");
    }

    static void testDateIsUsedAsGiven() {
        OffsetTime ot = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime first = ot.atDate(LocalDate.of(2021, 3, 1));
        OffsetDateTime second = ot.atDate(LocalDate.of(2021, 3, 2));
        check(!first.equals(second), "different dates must yield different results");
        check(first.toLocalDate().getDayOfMonth() == 1, "first date day mismatch");
        check(second.toLocalDate().getDayOfMonth() == 2, "second date day mismatch");
        check(first.toLocalTime().equals(second.toLocalTime()), "time must be unchanged across dates");
    }

    static void testNullDateThrowsNPE() {
        OffsetTime ot = OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC);
        boolean thrown = false;
        try {
            ot.atDate(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null date should throw NullPointerException");
    }
}
