/*
 * @test
 * @summary Test appendLiteral(String) method of DateTimeFormatterBuilder for null and non-empty literals
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendLiteralNull();
        testAppendLiteralEmpty();
        testAppendLiteralSingleChar();
        testAppendLiteralMultipleChars();
    }

    private static void testAppendLiteralNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLiteral(null);
            throw new AssertionError("Expected NullPointerException for null literal");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testAppendLiteralEmpty() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("");
        if (result != builder) {
            throw new AssertionError("appendLiteral("") should return the same builder instance");
        }
    }

    private static void testAppendLiteralSingleChar() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("A");
        if (result != builder) {
            throw new AssertionError("appendLiteral(single char) should return the same builder instance");
        }
    }

    private static void testAppendLiteralMultipleChars() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("Test");
        if (result != builder) {
            throw new AssertionError("appendLiteral(multiple chars) should return the same builder instance");
        }
    }
}
