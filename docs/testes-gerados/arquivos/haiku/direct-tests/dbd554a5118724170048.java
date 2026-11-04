/*
 * @test
 * @summary Verifies observable behavior of DateTimeFormatterBuilder.toFormatter(Locale)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testLocalePropagated();
        testDefaultResolverStyleIsSmart();
        testChronologyIsNotSet();
        testFormattingUsesBuilderPattern();
        testParsingUsesBuilderPattern();
        testDifferentLocalesProduceIndependentFormatters();
    }

    static DateTimeFormatterBuilder yearMonthBuilder() {
        return new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR, 2);
    }

    static void testLocalePropagated() {
        DateTimeFormatter f = yearMonthBuilder().toFormatter(Locale.US);
        check(Locale.US.equals(f.getLocale()), "locale should be propagated");
    }

    static void testDefaultResolverStyleIsSmart() {
        DateTimeFormatter f = yearMonthBuilder().toFormatter(Locale.US);
        check(f.getResolverStyle() == ResolverStyle.SMART, "resolver style should be SMART");
    }

    static void testChronologyIsNotSet() {
        DateTimeFormatter f = yearMonthBuilder().toFormatter(Locale.US);
        check(f.getChronology() == null, "chronology should be null");
    }

    static void testFormattingUsesBuilderPattern() {
        DateTimeFormatter f = yearMonthBuilder().toFormatter(Locale.US);
        String out = f.format(LocalDate.of(2018, 9, 1));
        check("2018-09".equals(out), "unexpected format output: " + out);
    }

    static void testParsingUsesBuilderPattern() {
        DateTimeFormatter f = yearMonthBuilder().toFormatter(Locale.US);
        TemporalAccessor parsed = f.parse("2018-09");
        check(parsed.get(ChronoField.YEAR) == 2018, "parsed year mismatch");
        check(parsed.get(ChronoField.MONTH_OF_YEAR) == 9, "parsed month mismatch");
    }

    static void testDifferentLocalesProduceIndependentFormatters() {
        DateTimeFormatter us = yearMonthBuilder().toFormatter(Locale.US);
        DateTimeFormatter de = yearMonthBuilder().toFormatter(Locale.GERMANY);
        check(Locale.US.equals(us.getLocale()), "US locale mismatch");
        check(Locale.GERMANY.equals(de.getLocale()), "GERMANY locale mismatch");
        check(!us.getLocale().equals(de.getLocale()), "locales should differ");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
