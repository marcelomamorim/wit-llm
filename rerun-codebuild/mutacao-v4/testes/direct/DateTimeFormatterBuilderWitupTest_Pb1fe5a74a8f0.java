/* @test
   @summary Tests the padNext method of DateTimeFormatterBuilder.
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
        builder.padNext(3, '*');
        System.out.println("testPadNextValid passed");
    }
    private static void testPadNextInvalid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.padNext(0, '*');
            throw new AssertionError("Expected IllegalArgumentException for padWidth=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        System.out.println("testPadNextInvalid passed");
    }
}
