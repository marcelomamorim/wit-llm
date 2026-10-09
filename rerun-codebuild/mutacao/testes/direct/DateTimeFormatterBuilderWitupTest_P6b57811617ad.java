/*
 * @test
 * @summary Test appendInstant method of DateTimeFormatterBuilder for valid and invalid inputs
 * @run main DateTimeFormatterBuilderWitupTest_P6b57811617ad
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

public class DateTimeFormatterBuilderWitupTest_P6b57811617ad {

    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalid();
    }

    static void testAppendInstantValid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(3);
            builder.appendInstant(0);
            builder.appendInstant(-1);
            builder.appendInstant(9);
            System.out.println("testAppendInstantValid passed");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid inputs", e);
        }
    }

    static void testAppendInstantInvalid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(-2);
            throw new RuntimeException("Expected IllegalArgumentException for fractionalDigits = -2");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(10);
            throw new RuntimeException("Expected IllegalArgumentException for fractionalDigits = 10");
        } catch (IllegalArgumentException e) {
            // expected
        }
        System.out.println("testAppendInstantInvalid passed");
    }
}
