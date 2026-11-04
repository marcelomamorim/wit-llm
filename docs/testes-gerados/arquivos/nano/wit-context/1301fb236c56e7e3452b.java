/*
 * @test
 * @summary Test appendText method for null and valid TemporalField
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendTextWithNullField();
        testAppendTextWithValidField();
    }

    private static void testAppendTextWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText((TemporalField) null);
            System.out.println("FAIL: Expected NullPointerException for null field");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null field");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testAppendTextWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(ChronoField.MONTH_OF_YEAR);
            System.out.println("PASS: appendText with valid field executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid field: " + e);
        }
    }
}
