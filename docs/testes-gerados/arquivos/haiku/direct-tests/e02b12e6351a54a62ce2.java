/*
 * @test
 * @summary Tests DateTimeFormatterBuilder.optionalEnd() observable behavior
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testOptionalEndWithoutStartThrows();
        testOptionalEndReturnsSameBuilder();
        testOptionalSectionSkippedWhenAbsent();
        testOptionalSectionParsedWhenPresent();
        testNestedEmptyOptionalSections();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOptionalEndWithoutStartThrows() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().optionalEnd();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "optionalEnd() without optionalStart() must throw IllegalStateException");
    }

    static void testOptionalEndReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        b.optionalStart();
        check(b.optionalEnd() == b, "optionalEnd() must return this builder");
    }

    static void testOptionalSectionSkippedWhenAbsent() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .optionalStart()
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .optionalEnd()
                .toFormatter();
        TemporalAccessor t = f.parse("2020");
        check(t.isSupported(ChronoField.YEAR), "YEAR must be parsed");
        check(!t.isSupported(ChronoField.MONTH_OF_YEAR), "optional month must be absent when not in input");
    }

    static void testOptionalSectionParsedWhenPresent() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .optionalStart()
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .optionalEnd()
                .toFormatter();
        TemporalAccessor t = f.parse("2020-05");
        check(t.getLong(ChronoField.MONTH_OF_YEAR) == 5L, "optional month must be parsed when present");
    }

    static void testNestedEmptyOptionalSections() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        b.appendValue(ChronoField.YEAR);
        b.optionalStart();
        b.optionalStart();
        b.optionalEnd();
        b.optionalEnd();
        DateTimeFormatter f = b.toFormatter();
        check("2020".equals(f.format(LocalDate.of(2020, 1, 1))),
              "empty nested optional sections must not add output");

        boolean thrown = false;
        try {
            b.optionalEnd();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "extra optionalEnd() must throw IllegalStateException");
    }
}
