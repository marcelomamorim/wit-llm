/*
 * @test
 * @summary Witup-generated checks for DateTimeFormatterBuilder.appendLocalizedOffset(TextStyle) null and invalid style handling
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullStyleThrowsNPE();
        testFullStyleReturnsSameBuilder();
        testShortStyleReturnsSameBuilder();
        testOtherStylesThrowIAE();
    }

    static void testNullStyleThrowsNPE() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        try {
            b.appendLocalizedOffset(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null style");
    }

    static void testFullStyleReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = b.appendLocalizedOffset(TextStyle.FULL);
        if (result != b) {
            throw new RuntimeException("appendLocalizedOffset(FULL) must return this builder");
        }
        DateTimeFormatter f = b.toFormatter();
        if (f == null) {
            throw new RuntimeException("toFormatter() returned null");
        }
    }

    static void testShortStyleReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = b.appendLocalizedOffset(TextStyle.SHORT);
        if (result != b) {
            throw new RuntimeException("appendLocalizedOffset(SHORT) must return this builder");
        }
        DateTimeFormatter f = b.toFormatter();
        if (f == null) {
            throw new RuntimeException("toFormatter() returned null");
        }
    }

    static void testOtherStylesThrowIAE() {
        int checked = 0;
        for (TextStyle style : TextStyle.values()) {
            if (style == TextStyle.FULL || style == TextStyle.SHORT) {
                continue;
            }
            checked++;
            DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
            try {
                b.appendLocalizedOffset(style);
            } catch (IllegalArgumentException expected) {
                continue;
            }
            throw new RuntimeException("Expected IllegalArgumentException for style " + style);
        }
        if (checked == 0) {
            throw new RuntimeException("No additional TextStyle values were available to test");
        }
    }
}
