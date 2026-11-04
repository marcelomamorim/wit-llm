/*
 * @test
 * @summary Test the format method of Year class with various formatters
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

public class YearWitupTest {

    public static void main(String[] args) {
        testFormatWithDefaultFormatter();
        testFormatWithCustomFormatter();
        testFormatWithNullFormatter();
    }

    static void testFormatWithDefaultFormatter() {
        Year year = Year.of(2023);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String result = year.format(formatter);
        assert result.equals("2023") : "Expected '2023', got '" + result + "'";
    }

    static void testFormatWithCustomFormatter() {
        Year year = Year.of(1999);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 4, SignStyle.EXCEEDS_PAD)
            .toFormatter();
        String result = year.format(formatter);
        assert result.equals("1999") : "Expected '1999', got '" + result + "'";
    }

    static void testFormatWithNullFormatter() {
        Year year = Year.of(2020);
        try {
            year.format(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
