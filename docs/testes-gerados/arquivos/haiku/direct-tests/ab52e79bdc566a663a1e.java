/*
 * @test
 * @summary appendLiteral: null rejection, empty literal, single-char and multi-char literals, and chaining
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullLiteralThrows();
        testEmptyLiteralReturnsSameBuilderAndPrintsNothing();
        testSingleCharLiteralIsPrinted();
        testMultiCharLiteralIsPrinted();
        testChainingAppendsInOrder();
        testSingleQuoteLiteral();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String format(DateTimeFormatterBuilder builder) {
        return builder.toFormatter().format(LocalDate.of(2020, 1, 1));
    }

    static void testNullLiteralThrows() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLiteral((String) null);
            throw new RuntimeException("expected NullPointerException for null literal");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyLiteralReturnsSameBuilderAndPrintsNothing() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("");
        check(result == builder, "appendLiteral(\"\") must return this");
        check(format(builder).equals(""), "empty literal must print nothing");
    }

    static void testSingleCharLiteralIsPrinted() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("-");
        check(result == builder, "appendLiteral(\"-\") must return this");
        check(format(builder).equals("-"), "single-char literal not printed as expected");
    }

    static void testMultiCharLiteralIsPrinted() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLiteral("abc");
        check(result == builder, "appendLiteral(\"abc\") must return this");
        check(format(builder).equals("abc"), "multi-char literal not printed as expected");
    }

    static void testChainingAppendsInOrder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLiteral("a").appendLiteral("bc").appendLiteral("").appendLiteral("d");
        check(format(builder).equals("abcd"), "chained literals must be printed in order");
    }

    static void testSingleQuoteLiteral() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLiteral("'");
        check(format(builder).equals("'"), "single quote literal not printed as expected");
    }
}
