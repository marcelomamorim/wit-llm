/*
 * @test
 * @summary Tests the parse method of java.time.Year with custom DateTimeFormatter.
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

public class YearWitupTest {

    public static void main(String[] args) {
        testParseWithFormatter();
    }

    private static void testParseWithFormatter() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .toFormatter();

        String validYearString = "2023";
        Year parsedYear = Year.parse(validYearString, formatter);
        assert parsedYear.getValue() == 2023 : "Parsed year should be 2023";

        String invalidYearString = "20a3";
        try {
            Year.parse(invalidYearString, formatter);
            assert false : "Parsing invalid year string should throw DateTimeParseException";
        } catch (java.time.format.DateTimeParseException e) {
            // Expected exception
        }
    }
}
