/* @test    @summary Test for HijrahEra.of(int) method to verify correct behavior and exception handling.    @run main HijrahEraWitupTest */

import static java.time.temporal.ChronoField.ERA;
import java.time.DateTimeException;
import java.time.chrono.HijrahEra;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    private static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != null && era == HijrahEra.AH) {
            System.out.println("testValidEra passed");
        } else {
            System.out.println("testValidEra failed");
        }
    }

    private static void testInvalidEra() {
        try {
            HijrahEra.of(0);
            System.out.println("testInvalidEra failed");
        } catch (DateTimeException e) {
            System.out.println("testInvalidEra passed");
        }
        try {
            HijrahEra.of(2);
            System.out.println("testInvalidEra failed");
        } catch (DateTimeException e) {
            System.out.println("testInvalidEra passed");
        }
    }
}
