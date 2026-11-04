/*
 * @test
 * @summary Test appendLiteral method of DateTimeFormatterBuilder for various literal inputs
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendLiteralEmpty();
        testAppendLiteralSingleChar();
        testAppendLiteralMultipleChars();
    }

    static void testAppendLiteralEmpty() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLiteral("");
        // No exception expected, builder should be returned
        System.out.println("testAppendLiteralEmpty passed");
    }

    static void testAppendLiteralSingleChar() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLiteral("A");
        System.out.println("testAppendLiteralSingleChar passed");
    }

    static void testAppendLiteralMultipleChars() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLiteral("Hello");
        System.out.println("testAppendLiteralMultipleChars passed");
    }
}
