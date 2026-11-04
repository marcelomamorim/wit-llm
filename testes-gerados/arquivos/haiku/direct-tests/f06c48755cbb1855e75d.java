/*
 * @test
 * @summary Tests DateTimeFormatter.ofLocalizedTime(FormatStyle) null check and basic observable behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullTimeStyleThrowsNPE();
        testAllStylesReturnFormatter();
        testShortStyleRoundTrip();
        testFormattingIsDeterministic();
    }

    static void testNullTimeStyleThrowsNPE() {
        boolean thrown = false;
        try {
            DateTimeFormatter.ofLocalizedTime(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null timeStyle");
        }
    }

    static void testAllStylesReturnFormatter() {
        FormatStyle[] styles = FormatStyle.values();
        for (FormatStyle style : styles) {
            DateTimeFormatter f = DateTimeFormatter.ofLocalizedTime(style);
            if (f == null) {
                throw new RuntimeException("Null formatter for style " + style);
            }
        }
    }

    static void testShortStyleRoundTrip() {
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime time = LocalTime.of(10, 15);
        String text = f.format(time);
        if (text == null || text.isEmpty()) {
            throw new RuntimeException("Formatted text is empty");
        }
        LocalTime parsed = LocalTime.parse(text, f);
        if (!time.equals(parsed)) {
            throw new RuntimeException("Round trip failed: " + text + " -> " + parsed);
        }
    }

    static void testFormattingIsDeterministic() {
        DateTimeFormatter f1 = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        DateTimeFormatter f2 = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        LocalTime time = LocalTime.of(23, 59, 1);
        String a = f1.format(time);
        String b = f2.format(time);
        if (!a.equals(b)) {
            throw new RuntimeException("Expected same output, got " + a + " and " + b);
        }
    }
}
