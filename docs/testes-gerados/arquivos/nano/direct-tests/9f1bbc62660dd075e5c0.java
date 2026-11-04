/*
 * @test
 * @summary Test parseDefaulting method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testParseDefaultingWithValidField();
        testParseDefaultingWithNullField();
    }

    static void testParseDefaultingWithValidField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.parseDefaulting(ChronoField.YEAR, 2023L);
        // No exception expected, verify internal state if needed
        System.out.println("testParseDefaultingWithValidField passed");
    }

    static void testParseDefaultingWithNullField() {
        boolean exceptionThrown = false;
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.parseDefaulting(null, 2023L);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null field");
        }
        System.out.println("testParseDefaultingWithNullField passed");
    }
}
