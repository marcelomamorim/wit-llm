/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.parse(CharSequence, DateTimeFormatter) null handling and basic parsing
 * @run main LocalDateTimeWitupTest
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNpe();
        testNullTextThrowsNpe();
        testValidParse();
    }

    static void testNullFormatterThrowsNpe() {
        try {
            LocalDateTime.parse("2020-01-02T03:04:05", null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when formatter is null");
    }

    static void testNullTextThrowsNpe() {
        try {
            LocalDateTime.parse(null, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when text is null");
    }

    static void testValidParse() {
        LocalDateTime actual = LocalDateTime.parse("2020-01-02T03:04:05", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime expected = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
        if (!expected.equals(actual)) {
            throw new RuntimeException("unexpected parse result: " + actual);
        }
    }
}
