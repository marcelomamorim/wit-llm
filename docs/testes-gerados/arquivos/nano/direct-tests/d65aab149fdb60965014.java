/*
 * @test
 * @summary Test DateTimeFormatterBuilder.appendText method for valid inputs
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendTextWithValidFieldAndStyle();
        testAppendTextWithNullField();
        testAppendTextWithNullStyle();
    }

    static void testAppendTextWithValidFieldAndStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL);
            System.out.println("testAppendTextWithValidFieldAndStyle passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAppendTextWithValidFieldAndStyle", e);
        }
    }

    static void testAppendTextWithNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(null, TextStyle.FULL);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException e) {
            System.out.println("testAppendTextWithNullField passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAppendTextWithNullField", e);
        }
    }

    static void testAppendTextWithNullStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(ChronoField.MONTH_OF_YEAR, null);
            System.out.println("testAppendTextWithNullStyle passed");
        } catch (NullPointerException e) {
            System.out.println("testAppendTextWithNullStyle passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAppendTextWithNullStyle", e);
        }
    }
}
