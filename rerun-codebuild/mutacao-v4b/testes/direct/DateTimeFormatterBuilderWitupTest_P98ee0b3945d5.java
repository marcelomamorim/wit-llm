/*
 * @test
 * @summary Tests appendInstant method of DateTimeFormatterBuilder for valid and invalid inputs.
 * @run main DateTimeFormatterBuilderWitupTest_P98ee0b3945d5
 */
import java.time.format.DateTimeFormatterBuilder;
public class DateTimeFormatterBuilderWitupTest_P98ee0b3945d5 {
    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalid();
    }

    static void testAppendInstantValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendInstant(0); // valid, fractionalDigits = 0
        builder.appendInstant(9); // valid, fractionalDigits = 9
        builder.appendInstant(-1); // valid, fractionalDigits = -1
    }

    static void testAppendInstantInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.appendInstant(10); // invalid, fractionalDigits > 9
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=10");
        }
        exceptionThrown = false;
        try {
            builder.appendInstant(-2); // invalid, fractionalDigits < -1
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=-2");
        }
    }
}
