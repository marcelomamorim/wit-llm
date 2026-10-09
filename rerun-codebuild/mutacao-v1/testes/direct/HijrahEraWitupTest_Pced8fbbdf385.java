/* @test    @summary Test the static method HijrahEra.of(int) for valid and invalid inputs    @run main HijrahEraWitupTest_Pced8fbbdf385 */

import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest_Pced8fbbdf385 {

    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != null && era == HijrahEra.AH) {
            System.out.println("PASS: Valid era 1 returns AH");
        } else {
            throw new AssertionError("FAIL: Valid era 1 did not return AH");
        }
    }

    static void testInvalidEra() {
        try {
            HijrahEra.of(0);
            throw new AssertionError("FAIL: Invalid era 0 did not throw exception");
        } catch (DateTimeException e) {
            System.out.println("PASS: Invalid era 0 threw DateTimeException");
        }
        try {
            HijrahEra.of(2);
            throw new AssertionError("FAIL: Invalid era 2 did not throw exception");
        } catch (DateTimeException e) {
            System.out.println("PASS: Invalid era 2 threw DateTimeException");
        }
    }
}
