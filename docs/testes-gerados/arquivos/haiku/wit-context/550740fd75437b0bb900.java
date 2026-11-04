/*
 * @test
 * @summary Checks OffsetTime.atDate for valid dates and null date handling
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testAtDateCombinesFields();
        testAtDateWithNegativeOffset();
        testAtDateNullDateThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAtDateCombinesFields() {
        LocalDate date = LocalDate.of(2018, 9, 15);
        LocalTime time = LocalTime.of(10, 15, 30, 500_000_000);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetTime ot = OffsetTime.of(time, offset);

        OffsetDateTime odt = ot.atDate(date);

        check(odt != null, "atDate returned null");
        check(date.equals(odt.toLocalDate()), "date mismatch: " + odt.toLocalDate());
        check(time.equals(odt.toLocalTime()), "time mismatch: " + odt.toLocalTime());
        check(offset.equals(odt.getOffset()), "offset mismatch: " + odt.getOffset());
    }

    static void testAtDateWithNegativeOffset() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        OffsetTime ot = OffsetTime.of(LocalTime.MIDNIGHT, ZoneOffset.ofHours(-5));

        OffsetDateTime odt = ot.atDate(date);

        check(date.equals(odt.toLocalDate()), "date mismatch with negative offset");
        check(LocalTime.MIDNIGHT.equals(odt.toLocalTime()), "time mismatch with negative offset");
        check(ZoneOffset.ofHours(-5).equals(odt.getOffset()), "offset mismatch with negative offset");
    }

    static void testAtDateNullDateThrowsNPE() {
        OffsetTime ot = OffsetTime.of(LocalTime.NOON, ZoneOffset.UTC);
        boolean thrown = false;
        try {
            ot.atDate(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "atDate(null) did not throw NullPointerException");
    }
}
