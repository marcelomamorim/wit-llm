/* @test
   @summary Testa o método padNext para valores válidos e inválidos.
   @run main DateTimeFormatterBuilderWitupTest_Pb1fe5a74a8f0
 */
import java.time.format.DateTimeFormatterBuilder;
public class DateTimeFormatterBuilderWitupTest_Pb1fe5a74a8f0 {
    public static void main(String[] args) {
        testPadNextValid();
        testPadNextInvalid();
    }
    private static void testPadNextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(1, '*');
            builder.padNext(5, '-');
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid padWidth", e);
        }
    }
    private static void testPadNextInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean exceptionThrown = false;
        try {
            builder.padNext(0, '+');
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for padWidth < 1");
        }
        exceptionThrown = false;
        try {
            builder.padNext(-3, '+');
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative padWidth");
        }
    }
}
