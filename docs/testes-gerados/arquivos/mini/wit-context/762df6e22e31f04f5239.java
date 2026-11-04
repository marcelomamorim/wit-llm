/*
 * @test
 * @summary Deterministic tests for java.time.YearMonth.parse(CharSequence, DateTimeFormatter)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInputs();
    }

    private static void testParseWithNullFormatter() {
        try {
            YearMonth.parse("2018-09", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        try {
            YearMonth.parse(null, formatter);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithValidInputs() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth ym = YearMonth.parse("2018-09", formatter);
        if (ym.getYear() != 2018 || ym.getMonthValue() != 9) {
            throw new AssertionError("Parsed YearMonth does not match expected values");
        }

        // Test with another valid input
        YearMonth ym2 = YearMonth.parse("0001-01", formatter);
        if (ym2.getYear() != 1 || ym2.getMonthValue() != 1) {
            throw new AssertionError("Parsed YearMonth does not match expected values for year 1 month 1");
        }

        // Test parse failure with invalid format
        try {
            YearMonth.parse("2018/09", formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid format");
        } catch (DateTimeParseException e) {
            // Expected
        }
    }
}
