/*
 * @test
 * @summary Tests DateTimeFormatterBuilder.appendChronologyText(TextStyle) for returned builder, null argument handling and formatting output
 * @run main DateTimeFormatterBuilderWitupTest
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testReturnsSameBuilder();
        testNullTextStyleThrowsNPE();
        testFormatProducesNonEmptyChronologyText();
        testFormatIsDeterministic();
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder r = b.appendChronologyText(TextStyle.FULL);
        if (r != b) {
            throw new RuntimeException("appendChronologyText must return the same builder instance");
        }
    }

    static void testNullTextStyleThrowsNPE() {
        try {
            new DateTimeFormatterBuilder().appendChronologyText(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null textStyle");
    }

    static void testFormatProducesNonEmptyChronologyText() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendChronologyText(TextStyle.FULL)
                .toFormatter(Locale.ROOT);
        String s = f.format(LocalDate.of(2018, 1, 1));
        if (s == null || s.isEmpty()) {
            throw new RuntimeException("Chronology text output must not be empty, got: " + s);
        }
    }

    static void testFormatIsDeterministic() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendChronologyText(TextStyle.FULL)
                .toFormatter(Locale.ROOT);
        String first = f.format(LocalDate.of(2018, 1, 1));
        String second = f.format(LocalDate.of(2018, 1, 1));
        if (!first.equals(second)) {
            throw new RuntimeException("Formatting must be deterministic: " + first + " vs " + second);
        }
    }
}
