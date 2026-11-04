/*
 * @test
 * @summary Test appendLocalizedOffset(TextStyle) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testNullStyleThrowsNPE();
        testInvalidStyleThrowsIAE();
        testValidStyles();
    }

    private static void testNullStyleThrowsNPE() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalizedOffset(null);
            throw new AssertionError("Expected NullPointerException for null style");
        } catch (NullPointerException npe) {
            // expected
            if (!"style".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testInvalidStyleThrowsIAE() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TextStyle invalidStyle = TextStyle.NARROW; // NARROW is not allowed
        try {
            builder.appendLocalizedOffset(invalidStyle);
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("Style must be either full or short")) {
                throw new AssertionError("IllegalArgumentException message mismatch", iae);
            }
        }
    }

    private static void testValidStyles() {
        DateTimeFormatterBuilder builderFull = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder resultFull = builderFull.appendLocalizedOffset(TextStyle.FULL);
        if (resultFull != builderFull) {
            throw new AssertionError("appendLocalizedOffset should return this for FULL style");
        }

        DateTimeFormatterBuilder builderShort = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder resultShort = builderShort.appendLocalizedOffset(TextStyle.SHORT);
        if (resultShort != builderShort) {
            throw new AssertionError("appendLocalizedOffset should return this for SHORT style");
        }
    }
}
