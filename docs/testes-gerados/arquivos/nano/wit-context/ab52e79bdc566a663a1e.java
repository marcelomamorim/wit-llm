/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendLiteralNull();
        testAppendLiteralEmpty();
        testAppendLiteralSingleChar();
        testAppendLiteralMultipleChars();
    }

    private static void testAppendLiteralNull() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLiteral(null);
            System.out.println("testAppendLiteralNull: FAILED (expected NullPointerException)");
        } catch (NullPointerException e) {
            System.out.println("testAppendLiteralNull: PASSED");
        } catch (Exception e) {
            System.out.println("testAppendLiteralNull: FAILED (unexpected exception: " + e + ")");
        }
    }

    private static void testAppendLiteralEmpty() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLiteral("");
            System.out.println("testAppendLiteralEmpty: PASSED");
        } catch (Exception e) {
            System.out.println("testAppendLiteralEmpty: FAILED (unexpected exception: " + e + ")");
        }
    }

    private static void testAppendLiteralSingleChar() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLiteral("A");
            System.out.println("testAppendLiteralSingleChar: PASSED");
        } catch (Exception e) {
            System.out.println("testAppendLiteralSingleChar: FAILED (unexpected exception: " + e + ");");
        }
    }

    private static void testAppendLiteralMultipleChars() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLiteral("ABCD");
            System.out.println("testAppendLiteralMultipleChars: PASSED");
        } catch (Exception e) {
            System.out.println("testAppendLiteralMultipleChars: FAILED (unexpected exception: " + e + ");");
        }
    }
}
