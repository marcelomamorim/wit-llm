/* @test
   @summary Tests the static method HijrahEra.of(int).
   @run main HijrahEraWitupTest_P12569cb288e7
 */
import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest_P12569cb288e7 {
    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != null && era == HijrahEra.AH) {
            System.out.println("testValidEra passed");
        } else {
            throw new AssertionError("Expected HijrahEra.AH for input 1");
        }
    }

    static void testInvalidEra() {
        try {
            HijrahEra.of(0);
            throw new AssertionError("Expected DateTimeException for input 0");
        } catch (DateTimeException e) {
            System.out.println("testInvalidEra passed");
        }
        try {
            HijrahEra.of(2);
            throw new AssertionError("Expected DateTimeException for input 2");
        } catch (DateTimeException e) {
            System.out.println("testInvalidEra passed");
        }
    }
}
