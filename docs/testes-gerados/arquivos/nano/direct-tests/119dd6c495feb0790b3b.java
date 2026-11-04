/*
 * @test
 * @summary Test the format method of LocalDate with various formatters
 * @run main LocalDateWitupTest
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testFormatWithISO_LOCAL_DATE();
        testFormatWithCustomPattern();
        testFormatWithBuilder();
    }

    static void testFormatWithISO_LOCAL_DATE() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String result = date.format(formatter);
        if (!"2023-03-15".equals(result)) {
            throw new AssertionError("Expected '2023-03-15' but got: " + result);
        }
    }

    static void testFormatWithCustomPattern() {
        LocalDate date = LocalDate.of(2023, 12, 5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String result = date.format(formatter);
        if (!"05/12/2023".equals(result)) {
            throw new AssertionError("Expected '05/12/2023' but got: " + result);
        }
    }

    static void testFormatWithBuilder() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy")
            .appendLiteral("-")
            .appendPattern("MM")
            .appendLiteral("-")
            .appendPattern("dd")
            .toFormatter();
        LocalDate date = LocalDate.of(2024, 1, 9);
        String result = date.format(formatter);
        if (!"2024-01-09".equals(result)) {
            throw new AssertionError("Expected '2024-01-09' but got: " + result);
        }
    }
}
