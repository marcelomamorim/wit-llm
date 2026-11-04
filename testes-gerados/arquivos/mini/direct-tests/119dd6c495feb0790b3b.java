/*
 * @test
 * @summary Test java.time.LocalDate.format(DateTimeFormatter) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) throws Exception {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
        testFormatWithFormatterThrowingException();
    }

    private static void testFormatWithValidFormatter() {
        LocalDate date = LocalDate.of(2018, Month.SEPTEMBER, 25);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String result = date.format(formatter);
        if (!"2018-09-25".equals(result)) {
            throw new AssertionError("Expected '2018-09-25' but got '" + result + "'");
        }
    }

    private static void testFormatWithNullFormatter() {
        LocalDate date = LocalDate.of(2018, 9, 25);
        try {
            date.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testFormatWithFormatterThrowingException() {
        LocalDate date = LocalDate.of(2018, 9, 25);
        DateTimeFormatter badFormatter = new DateTimeFormatter() {
            @Override
            public String format(java.time.temporal.TemporalAccessor temporal) {
                throw new DateTimeParseException("format error", "", 0);
            }
        };
        try {
            date.format(badFormatter);
            throw new AssertionError("Expected DateTimeParseException from formatter");
        } catch (DateTimeParseException ex) {
            // expected
        }
    }
}
