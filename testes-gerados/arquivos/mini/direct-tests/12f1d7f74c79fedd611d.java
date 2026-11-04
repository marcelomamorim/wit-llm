/*
 * @test
 * @summary Test appendLocalizedOffset method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendLocalizedOffsetWithFullStyle();
        testAppendLocalizedOffsetWithShortStyle();
        testAppendLocalizedOffsetWithNullStyle();
        testAppendLocalizedOffsetWithInvalidStyle();
    }

    private static void testAppendLocalizedOffsetWithFullStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalizedOffset(TextStyle.FULL);
        if (result != builder) {
            throw new AssertionError("appendLocalizedOffset should return this instance when style is FULL");
        }
    }

    private static void testAppendLocalizedOffsetWithShortStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalizedOffset(TextStyle.SHORT);
        if (result != builder) {
            throw new AssertionError("appendLocalizedOffset should return this instance when style is SHORT");
        }
    }

    private static void testAppendLocalizedOffsetWithNullStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalizedOffset(null);
            throw new AssertionError("appendLocalizedOffset should throw NullPointerException when style is null");
        } catch (NullPointerException expected) {
        }
    }

    private static void testAppendLocalizedOffsetWithInvalidStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Create a dummy TextStyle not FULL or SHORT by using an anonymous subclass
        TextStyle invalidStyle = new TextStyle("INVALID", 0) {};
        try {
            builder.appendLocalizedOffset(invalidStyle);
            throw new AssertionError("appendLocalizedOffset should throw IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException expected) {
        }
    }
}
