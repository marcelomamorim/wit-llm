/*
 * @test
 * @summary Verifies ZonedDateTime.parse(CharSequence, DateTimeFormatter) observable behavior
 * @run main ZonedDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        testIsoZonedDateTimeWithRegionZone();
        testIsoOffsetDateTimeYieldsOffsetZone();
        testCustomPatternWithZoneId();
        testNullFormatterThrowsNpe();
        testFormatterWithoutZoneFails();
        testUnparseableTextFails();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testIsoZonedDateTimeWithRegionZone() {
        ZonedDateTime zdt = ZonedDateTime.parse(
                "2007-12-03T10:15:30+01:00[Europe/Paris]", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        check(zdt.getYear() == 2007, "year");
        check(zdt.getMonthValue() == 12, "month");
        check(zdt.getDayOfMonth() == 3, "day");
        check(zdt.getHour() == 10, "hour");
        check(zdt.getMinute() == 15, "minute");
        check(zdt.getSecond() == 30, "second");
        check(zdt.getZone().equals(ZoneId.of("Europe/Paris")), "region zone preserved");
        check(zdt.getOffset().equals(ZoneOffset.ofHours(1)), "offset +01:00");
    }

    static void testIsoOffsetDateTimeYieldsOffsetZone() {
        ZonedDateTime zdt = ZonedDateTime.parse(
                "2007-12-03T10:15:30+02:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        check(zdt.getZone().equals(ZoneOffset.ofHours(2)), "zone is offset +02:00");
        check(zdt.getHour() == 10, "hour with offset parse");
        check(zdt.getOffset().equals(ZoneOffset.ofHours(2)), "offset +02:00");
    }

    static void testCustomPatternWithZoneId() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV");
        ZonedDateTime zdt = ZonedDateTime.parse("2007-12-03 10:15 Europe/Paris", f);
        check(zdt.getZone().equals(ZoneId.of("Europe/Paris")), "pattern VV zone");
        check(zdt.toLocalDateTime().equals(LocalDateTime.of(2007, 12, 3, 10, 15)), "local date-time");
    }

    static void testNullFormatterThrowsNpe() {
        boolean thrown = false;
        try {
            ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null formatter must throw NullPointerException");
    }

    static void testFormatterWithoutZoneFails() {
        boolean thrown = false;
        try {
            ZonedDateTime.parse("2007-12-03T10:15:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            thrown = true;
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "local date-time without zone must not produce ZonedDateTime");
    }

    static void testUnparseableTextFails() {
        boolean thrown = false;
        try {
            ZonedDateTime.parse("not-a-date", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "invalid text must throw DateTimeParseException");
    }
}
