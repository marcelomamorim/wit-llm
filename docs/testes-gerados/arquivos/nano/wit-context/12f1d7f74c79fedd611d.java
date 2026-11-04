/*
 * @test
 * @summary Test appendLocalizedOffset with valid and invalid TextStyle
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendLocalizedOffsetValid();
        testAppendLocalizedOffsetNull();
        testAppendLocalizedOffsetInvalid();
    }

    static void testAppendLocalizedOffsetValid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalizedOffset(TextStyle.FULL);
            builder.appendLocalizedOffset(TextStyle.SHORT);
        } catch (Exception e) {
            throw new AssertionError("Valid styles should not throw", e);
        }
    }

    static void testAppendLocalizedOffsetNull() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalizedOffset(null);
            throw new AssertionError("Expected NullPointerException for null style");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Expected NullPointerException, got: " + e);
        }
    }

    static void testAppendLocalizedOffsetInvalid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            // Using a style not equal to FULL or SHORT
            builder.appendLocalizedOffset(TextStyle.NARROW);
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException, got: " + e);
        }
    }
}
