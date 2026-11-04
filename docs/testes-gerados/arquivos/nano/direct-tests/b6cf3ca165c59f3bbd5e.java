/*
 * @test
 * @summary Test OffsetDateTime.format with various formatters
 * @run main OffsetDateTimeWitupTest
 */
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testFormatWithISO();
        testFormatWithCustomPattern();
        testFormatWithNullFormatter();
    }

    static void testFormatWithISO() {
        OffsetDateTime odt = OffsetDateTime.of(2023, 3, 15, 10, 30, 45, 123456789, ZoneOffset.ofHours(-3));
        String result = odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String expected = "2023-03-15T10:30:45.123456789-03:00";
        if (!result.equals(expected)) {
            throw new AssertionError("ISO format mismatch: expected " + expected + " but got " + result);
        }
    }

    static void testFormatWithCustomPattern() {
        OffsetDateTime odt = OffsetDateTime.of(2022, 12, 25, 8, 0, 0, 0, ZoneOffset.ofHours(0));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String result = odt.format(formatter);
        String expected = "2022/12/25 08:00";
        if (!result.equals(expected)) {
            throw new AssertionError("Custom pattern format mismatch: expected " + expected + " but got " + result);
        }
    }

    static void testFormatWithNullFormatter() {
        OffsetDateTime odt = OffsetDateTime.now();
        try {
            odt.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
