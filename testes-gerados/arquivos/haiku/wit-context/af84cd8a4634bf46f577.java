/*
 * @test
 * @summary Witup generated tests for DateTimeFormatterBuilder.appendValue(TemporalField)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullFieldThrowsNullPointerException();
        testAppendValueReturnsSameBuilder();
        testFormatsYearWithAppendedValue();
    }

    static void testNullFieldThrowsNullPointerException() {
        try {
            new DateTimeFormatterBuilder().appendValue((ChronoField) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null field");
    }

    static void testAppendValueReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder returned = builder.appendValue(ChronoField.YEAR);
        if (returned != builder) {
            throw new RuntimeException("appendValue must return the same builder instance");
        }
    }

    static void testFormatsYearWithAppendedValue() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .toFormatter();
        String result = formatter.format(LocalDate.of(2020, 1, 1));
        if (!"2020".equals(result)) {
            throw new RuntimeException("unexpected format result: " + result);
        }
    }
}
