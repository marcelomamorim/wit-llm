/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.DateTimeFormatterBuilder;
import java.time.chrono.IsoChronology;
import java.util.Objects;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testOfLocalizedDateWithNull();
        testOfLocalizedDateWithValid();
    }

    public static void testOfLocalizedDateWithNull() {
        try {
            DateTimeFormatter.ofLocalizedDate(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testOfLocalizedDateWithValid() {
        FormatStyle style = FormatStyle.MEDIUM;
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(style);
        if (formatter == null) {
            throw new AssertionError("Formatter should not be null for valid style");
        }
    }
}
