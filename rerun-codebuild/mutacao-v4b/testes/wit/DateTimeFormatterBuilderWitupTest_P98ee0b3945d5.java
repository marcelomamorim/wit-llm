/* @test
   @summary Testa o método appendInstant com valores válidos e inválidos.
   @run main DateTimeFormatterBuilderWitupTest_P98ee0b3945d5
 */
import java.time.format.DateTimeFormatterBuilder;
public class DateTimeFormatterBuilderWitupTest_P98ee0b3945d5 {
    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalid();
    }
    private static void testAppendInstantValid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(0);
            builder.appendInstant(9);
            builder.appendInstant(-1);
        } catch (Exception e) {
            throw new AssertionError("Expected no exception for valid fractionalDigits", e);
        }
    }
    private static void testAppendInstantInvalid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(10);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=10");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(-2);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=-2");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
