/*
 * @test
 * @summary Test YearMonth.parse(CharSequence, DateTimeFormatter) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM");
        YearMonth ym = YearMonth.parse("2018-09", formatter);
        if (ym.getYear() != 2018 || ym.getMonthValue() != 9) {
            throw new AssertionError("Parsed YearMonth does not match expected values");
        }
    }

    private static void testParseNullFormatter() {
        try {
            YearMonth.parse("2018-09", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM");
        try {
            YearMonth.parse("invalid-text", formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
