/*
 * @test
 * @summary Verifies DateTimeFormatterBuilder.appendOptional(DateTimeFormatter) null handling, fluent return, and optional print/parse behavior
 * @run main DateTimeFormatterBuilderWitupTest
 */

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNullPointerException();
        testReturnsSameBuilder();
        testFormatWithOptionalSectionPresent();
        testParseWithOptionalSectionAbsent();
        testParseWithOptionalSectionPresent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFormatterThrowsNullPointerException() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendOptional(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendOptional(DateTimeFormatter.ofPattern("'-'MM"));
        check(result == builder, "appendOptional must return this builder");
    }

    static void testFormatWithOptionalSectionPresent() {
        DateTimeFormatter inner = DateTimeFormatter.ofPattern("'-'MM");
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4)
                .appendOptional(inner)
                .toFormatter();
        String s = f.format(LocalDate.of(2020, 1, 1));
        check("2020-01".equals(s), "expected 2020-01 but was " + s);
    }

    static void testParseWithOptionalSectionAbsent() {
        DateTimeFormatter inner = DateTimeFormatter.ofPattern("'-'MM");
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4)
                .appendOptional(inner)
                .toFormatter();
        TemporalAccessor ta = f.parse("2020");
        check(ta.getLong(YEAR) == 2020L, "year must be 2020");
    }

    static void testParseWithOptionalSectionPresent() {
        DateTimeFormatter inner = DateTimeFormatter.ofPattern("'-'MM");
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4)
                .appendOptional(inner)
                .toFormatter();
        TemporalAccessor ta = f.parse("2020-03");
        check(ta.getLong(YEAR) == 2020L, "year must be 2020");
        check(ta.getLong(MONTH_OF_YEAR) == 3L, "month must be 3");
    }
}
