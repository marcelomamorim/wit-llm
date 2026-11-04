/*
 * @test
 * @summary Verify OffsetDateTime.parse(CharSequence, DateTimeFormatter) for ISO and pattern-based input, null formatter and invalid text
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testIsoParse();
        testNanosAndUtc();
        testPatternWithOffset();
        testNullFormatter();
        testInvalidText();
        testMissingOffset();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testIsoParse() {
        OffsetDateTime odt = OffsetDateTime.parse("2007-12-03T10:15:30+01:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime expected = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneOffset.ofHours(1));
        check(odt.equals(expected), "ISO parse mismatch: " + odt);
    }

    static void testNanosAndUtc() {
        OffsetDateTime odt = OffsetDateTime.parse("2007-12-03T10:15:30.123456789Z",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        check(odt.getNano() == 123456789, "nanos mismatch: " + odt.getNano());
        check(odt.getOffset().equals(ZoneOffset.UTC), "offset mismatch: " + odt.getOffset());
    }

    static void testPatternWithOffset() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm Z");
        OffsetDateTime odt = OffsetDateTime.parse("2007-12-03 10:15 +0530", f);
        OffsetDateTime expected = OffsetDateTime.of(2007, 12, 3, 10, 15, 0, 0,
                ZoneOffset.ofHoursMinutes(5, 30));
        check(odt.equals(expected), "pattern parse mismatch: " + odt);
    }

    static void testNullFormatter() {
        try {
            OffsetDateTime.parse("2007-12-03T10:15:30+01:00", null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null formatter");
    }

    static void testInvalidText() {
        try {
            OffsetDateTime.parse("not a date", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeParseException for invalid text");
    }

    static void testMissingOffset() {
        try {
            OffsetDateTime.parse("2007-12-03T10:15:30", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeParseException when offset is missing");
    }
}
