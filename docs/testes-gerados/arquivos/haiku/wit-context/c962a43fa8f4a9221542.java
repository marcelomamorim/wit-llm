/*
 * @test
 * @summary Verify appendLocalized rejects two null styles and accepts a single non-null style
 * @run main DateTimeFormatterBuilderWitupTest
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testBothNullThrows();
        testSingleNonNullReturnsThis();
        testSingleNonNullFormats();
    }

    static void testBothNullThrows() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        try {
            b.appendLocalized(null, null);
            throw new RuntimeException("Expected IllegalArgumentException for null, null");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testSingleNonNullReturnsThis() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        if (b.appendLocalized(FormatStyle.SHORT, null) != b) {
            throw new RuntimeException("appendLocalized(date, null) should return this");
        }
        if (b.appendLocalized(null, FormatStyle.MEDIUM) != b) {
            throw new RuntimeException("appendLocalized(null, time) should return this");
        }
    }

    static void testSingleNonNullFormats() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder()
                .appendLocalized(FormatStyle.SHORT, null);
        String s = b.toFormatter(Locale.US).format(LocalDate.of(2020, 1, 1));
        if (s == null || s.isEmpty()) {
            throw new RuntimeException("Localized date formatter produced empty output");
        }
    }
}
