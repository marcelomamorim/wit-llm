/*
 * @test
 * @summary Test java.time.MonthDay.parse(CharSequence, DateTimeFormatter) for null arguments and normal parse
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseValid();
    }

    private static void testParseWithNullFormatter() {
        try {
            MonthDay.parse("--12-03", null);
            throw new RuntimeException("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("--MM-dd");
        try {
            MonthDay.parse(null, formatter);
            throw new RuntimeException("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("--MM-dd");
        MonthDay md = MonthDay.parse("--12-03", formatter);
        if (md.getMonthValue() != 12 || md.getDayOfMonth() != 3) {
            throw new RuntimeException("Parsed MonthDay has incorrect month or day");
        }
    }
}
