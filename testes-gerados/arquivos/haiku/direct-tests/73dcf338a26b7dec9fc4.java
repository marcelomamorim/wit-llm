/*
 * @test
 * @summary Checks DateTimeFormatter.parse(CharSequence, ParsePosition) null checks and successful positional parsing
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullText();
        testNullPosition();
        testParseFromStartIgnoresTrailingText();
        testParseFromOffset();
        System.out.println("All tests passed");
    }

    static void testNullText() {
        boolean thrown = false;
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(null, new ParsePosition(0));
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null text");
        }
    }

    static void testNullPosition() {
        boolean thrown = false;
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("2020-01-15", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null position");
        }
    }

    static void testParseFromStartIgnoresTrailingText() {
        ParsePosition pos = new ParsePosition(0);
        TemporalAccessor ta = DateTimeFormatter.ISO_LOCAL_DATE.parse("2020-01-15abc", pos);
        if (ta == null) {
            throw new RuntimeException("Expected non-null result");
        }
        if (pos.getIndex() != 10) {
            throw new RuntimeException("Expected index 10 but was " + pos.getIndex());
        }
        if (pos.getErrorIndex() != -1) {
            throw new RuntimeException("Expected no error but error index was " + pos.getErrorIndex());
        }
        if (!ta.isSupported(ChronoField.YEAR)
                || ta.getLong(ChronoField.YEAR) != 2020L) {
            throw new RuntimeException("Expected YEAR 2020");
        }
    }

    static void testParseFromOffset() {
        ParsePosition pos = new ParsePosition(2);
        TemporalAccessor ta = DateTimeFormatter.ISO_LOCAL_DATE.parse("xx2021-12-31", pos);
        if (ta == null) {
            throw new RuntimeException("Expected non-null result");
        }
        if (pos.getIndex() != 12) {
            throw new RuntimeException("Expected index 12 but was " + pos.getIndex());
        }
        if (ta.getLong(ChronoField.YEAR) != 2021L) {
            throw new RuntimeException("Expected YEAR 2021");
        }
    }
}
