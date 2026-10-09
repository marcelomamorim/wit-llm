/*
 * @test
 * @summary Test appendLocalized method of DateTimeFormatterBuilder for various style combinations.
 * @run main DateTimeFormatterBuilderWitupTest_Pa69c505aee49
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_Pa69c505aee49 {

    public static void main(String[] args) {
        testAppendLocalizedWithBothStyles();
        testAppendLocalizedWithNullDateStyle();
        testAppendLocalizedWithNullTimeStyle();
        testAppendLocalizedWithBothNullStyles();
    }

    static void testAppendLocalizedWithBothStyles() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(FormatStyle.SHORT, FormatStyle.MEDIUM);
        } catch (Exception e) {
            throw new AssertionError("Failed: appendLocalized with both styles", e);
        }
    }

    static void testAppendLocalizedWithNullDateStyle() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(null, FormatStyle.LONG);
        } catch (IllegalArgumentException e) {
            // Expected exception
            return;
        } catch (Exception e) {
            throw new AssertionError("Failed: appendLocalized with null date style", e);
        }
        throw new AssertionError("Expected IllegalArgumentException for null date style")
    }

    static void testAppendLocalizedWithNullTimeStyle() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(FormatStyle.FULL, null);
        } catch (IllegalArgumentException e) {
            // Expected exception
            return;
        } catch (Exception e) {
            throw new AssertionError("Failed: appendLocalized with null time style", e);
        }
        throw new AssertionError("Expected IllegalArgumentException for null time style")
    }

    static void testAppendLocalizedWithBothNullStyles() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(null, null);
        } catch (IllegalArgumentException e) {
            // Expected exception
            return;
        } catch (Exception e) {
            throw new AssertionError("Failed: appendLocalized with both styles null", e);
        }
        throw new AssertionError("Expected IllegalArgumentException for both styles null")
    }
}
