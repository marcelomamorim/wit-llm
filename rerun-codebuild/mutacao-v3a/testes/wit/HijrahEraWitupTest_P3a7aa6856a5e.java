/*
 * @test
 * @summary Test for HijrahEra.of(int) method to cover valid and invalid inputs
 * @run main HijrahEraWitupTest_P3a7aa6856a5e
 */

import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest_P3a7aa6856a5e {

    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    public static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != null && era == HijrahEra.AH) {
            System.out.println("testValidEra passed");
        } else {
            System.out.println("testValidEra failed");
        }
    }

    public static void testInvalidEra() {
        boolean thrown = false;
        try {
            HijrahEra.of(0); // invalid, should throw
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (thrown) {
            System.out.println("testInvalidEra passed");
        } else {
            System.out.println("testInvalidEra failed");
        }
    }
}
