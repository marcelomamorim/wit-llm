/*
 * @test
 * @summary Test appendLiteral method of DateTimeFormatterBuilder
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
            throw new RuntimeException("Expected NullPointerException for null literal");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAppendLiteralEmpty() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("");
        if (result != builder) {
            throw new RuntimeException("appendLiteral("") should return this");
        }
    }

    private static void testAppendLiteralSingleChar() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("A");
        if (result != builder) {
            throw new RuntimeException("appendLiteral(\"A\") should return this");
        }
    }

    private static void testAppendLiteralMultipleChars() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("ABC");
        if (result != builder) {
            throw new RuntimeException("appendLiteral(\"ABC\") should return this");
        }
    }
}
