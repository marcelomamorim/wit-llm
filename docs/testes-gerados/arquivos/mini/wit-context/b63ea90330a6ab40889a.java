/*
 * @test
 * @summary Deterministic tests for DateTimeFormatter.parseObject(String, ParsePosition)
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testNullTextThrowsNPE();
        testInvalidPositionReturnsNullAndSetsErrorIndex();
        testValidParseReturnsNonNull();
    }

    private static void testNullTextThrowsNPE() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        ParsePosition pos = new ParsePosition(0);
        try {
            formatter.parseObject(null, pos);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testInvalidPositionReturnsNullAndSetsErrorIndex() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        // Provide a ParsePosition with index beyond string length to cause parse failure
        ParsePosition pos = new ParsePosition(1000);
        Object result = formatter.parseObject("2020-01-01", pos);
        if (result != null) {
            throw new AssertionError("Expected null result for invalid parse position");
        }
        if (pos.getErrorIndex() < 0) {
            throw new AssertionError("Expected error index to be set on parse failure");
        }
    }

    private static void testValidParseReturnsNonNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        ParsePosition pos = new ParsePosition(0);
        Object result = formatter.parseObject("2020-01-01", pos);
        if (result == null) {
            throw new AssertionError("Expected non-null result for valid parse");
        }
        if (pos.getErrorIndex() != -1) {
            throw new AssertionError("Expected error index to be -1 for successful parse");
        }
    }
}
