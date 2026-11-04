/*
 * @test
 * @summary Witup generated tests for DateTimeFormatterBuilder.appendLiteral(String)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullLiteralThrowsNullPointerException();
        testReturnsSameBuilderInstance();
        testEmptyLiteralAppendsNothing();
        testSingleCharLiteral();
        testMultiCharLiteral();
        testMixedLiteralsAreConcatenatedInOrder();
    }

    static void testNullLiteralThrowsNullPointerException() {
        try {
            new DateTimeFormatterBuilder().appendLiteral((String) null);
            throw new RuntimeException("Expected NullPointerException for null literal");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsSameBuilderInstance() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("abc");
        check(result == builder, "appendLiteral must return this builder");
    }

    static void testEmptyLiteralAppendsNothing() {
        String out = new DateTimeFormatterBuilder()
                .appendLiteral("")
                .toFormatter()
                .format(LocalDate.of(2018, 9, 1));
        check(out.isEmpty(), "empty literal should produce empty output but was: " + out);
    }

    static void testSingleCharLiteral() {
        String out = new DateTimeFormatterBuilder()
                .appendLiteral("-")
                .toFormatter()
                .format(LocalDate.of(2018, 9, 1));
        check("-".equals(out), "expected '-' but was: " + out);
    }

    static void testMultiCharLiteral() {
        String out = new DateTimeFormatterBuilder()
                .appendLiteral("abc")
                .toFormatter()
                .format(LocalDate.of(2018, 9, 1));
        check("abc".equals(out), "expected 'abc' but was: " + out);
    }

    static void testMixedLiteralsAreConcatenatedInOrder() {
        String out = new DateTimeFormatterBuilder()
                .appendLiteral("a")
                .appendLiteral("")
                .appendLiteral("bc")
                .appendLiteral("-")
                .toFormatter()
                .format(LocalDate.of(2018, 9, 1));
        check("abc-".equals(out), "expected 'abc-' but was: " + out);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
