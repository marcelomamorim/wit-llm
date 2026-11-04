/*
 * @test
 * @summary Test HijrahEra.of(int) method for valid and invalid inputs
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest {
    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
        System.out.println("All tests passed.");
    }

    private static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != HijrahEra.AH) {
            throw new AssertionError("HijrahEra.of(1) should return AH singleton");
        }
    }

    private static void testInvalidEra() {
        try {
            HijrahEra.of(0);
            throw new AssertionError("HijrahEra.of(0) should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
        try {
            HijrahEra.of(2);
            throw new AssertionError("HijrahEra.of(2) should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
        try {
            HijrahEra.of(-1);
            throw new AssertionError("HijrahEra.of(-1) should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
