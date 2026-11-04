/*
 * @test
 * @summary Witup-derived tests for DateTimeFormatter.ofLocalizedDate
 * @run main DateTimeFormatterWitupTest
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullStyleThrowsNPE();
        testNonNullStylesProduceFormatter();
    }

    static void testNullStyleThrowsNPE() {
        try {
            DateTimeFormatter.ofLocalizedDate(null);
            throw new RuntimeException("NullPointerException expected for null dateStyle");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNonNullStylesProduceFormatter() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(style);
            if (formatter == null) {
                throw new RuntimeException("formatter must not be null for " + style);
            }
            String text = formatter.format(date);
            if (text == null || text.isEmpty()) {
                throw new RuntimeException("formatted text must not be empty for " + style);
            }
        }
    }
}
