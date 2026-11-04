/*
 * @test
 * @summary Test appendPattern method of DateTimeFormatterBuilder for various patterns.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendPatternSimple();
        testAppendPatternNull();
        testAppendPatternEmpty();
        testAppendPatternComplex();
    }

    static void testAppendPatternSimple() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = builder.toFormatter();
        String result = formatter.toString();
        if (!result.contains("yyyy-MM-dd")) {
            throw new AssertionError("Expected pattern to contain 'yyyy-MM-dd', but got: " + result);
        }
    }

    static void testAppendPatternNull() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendPattern(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testAppendPatternEmpty() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendPattern("");
        DateTimeFormatter formatter = builder.toFormatter();
        String result = formatter.toString();
        if (!result.contains("") && !result.isEmpty()) {
            throw new AssertionError("Expected empty pattern, but got: " + result);
        }
    }

    static void testAppendPatternComplex() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendPattern("yyyyMMdd'T'HHmmss");
        DateTimeFormatter formatter = builder.toFormatter();
        String result = formatter.toString();
        if (!result.contains("yyyyMMdd'T'HHmmss")) {
            throw new AssertionError("Expected pattern to contain 'yyyyMMdd'T'HHmmss', but got: " + result);
        }
    }
}
