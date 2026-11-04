/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.appendValue(TemporalField)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendValueWithNullField();
        testAppendValueWithValidField();
    }

    private static void testAppendValueWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue((TemporalField) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAppendValueWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            // Using ChronoField.YEAR as a valid TemporalField
            builder.appendValue(java.time.temporal.ChronoField.YEAR);
            System.out.println("PASS: appendValue with valid field succeeded");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
