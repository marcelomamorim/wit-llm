/*
 * @test
 * @summary Test java.time.chrono.HijrahEra.of(int) method
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest {
    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    private static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != HijrahEra.AH) {
            throw new AssertionError("Expected HijrahEra.AH for input 1");
        }
    }

    private static void testInvalidEra() {
        try {
            HijrahEra.of(0);
            throw new AssertionError("Expected DateTimeException for input 0");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            HijrahEra.of(2);
            throw new AssertionError("Expected DateTimeException for input 2");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            HijrahEra.of(-1);
            throw new AssertionError("Expected DateTimeException for input -1");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
