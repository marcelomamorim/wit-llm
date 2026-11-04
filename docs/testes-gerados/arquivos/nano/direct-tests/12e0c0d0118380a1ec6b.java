/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendValue method
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendValueValidWidth();
        testAppendValueInvalidWidth();
        testAppendValueNullField();
    }

    static void testAppendValueValidWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue(ChronoField.YEAR, 4);
            builder.appendValue(ChronoField.MONTH_OF_YEAR, 2);
            builder.appendValue(ChronoField.DAY_OF_MONTH, 2);
            System.out.println("testAppendValueValidWidth passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAppendValueValidWidth", e);
        }
    }

    static void testAppendValueInvalidWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.appendValue(ChronoField.YEAR, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for width=0");
        }
        exceptionThrown = false;
        try {
            builder.appendValue(ChronoField.YEAR, 20);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for width=20");
        }
        System.out.println("testAppendValueInvalidWidth passed");
    }

    static void testAppendValueNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.appendValue(null, 4);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null field");
        }
        System.out.println("testAppendValueNullField passed");
    }
}
