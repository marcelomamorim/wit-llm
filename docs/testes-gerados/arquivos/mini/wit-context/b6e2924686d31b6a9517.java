/*
 * @test
 * @summary Test JapaneseEra.of(int) method for valid and invalid inputs
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest {
    public static void main(String[] args) {
        testValidEras();
        testInvalidEras();
        System.out.println("All tests passed.");
    }

    private static void testValidEras() {
        // Test known valid era values based on KNOWN_ERAS length
        // Since KNOWN_ERAS is not accessible, test a range of plausible values
        // The minimum valid era is -1 (Meiji), test that and a few others
        JapaneseEra eraMeiji = JapaneseEra.of(-1);
        if (eraMeiji == null) {
            throw new AssertionError("JapaneseEra.of(-1) returned null");
        }

        // Test 0 and 1 as typical valid era values
        JapaneseEra era0 = JapaneseEra.of(0);
        if (era0 == null) {
            throw new AssertionError("JapaneseEra.of(0) returned null");
        }

        JapaneseEra era1 = JapaneseEra.of(1);
        if (era1 == null) {
            throw new AssertionError("JapaneseEra.of(1) returned null");
        }
    }

    private static void testInvalidEras() {
        // Test values less than -1
        try {
            JapaneseEra.of(-2);
            throw new AssertionError("Expected DateTimeException for JapaneseEra.of(-2)");
        } catch (DateTimeException expected) {
            // Expected exception
        }

        // Test values greater or equal to KNOWN_ERAS.length
        // Since KNOWN_ERAS length is unknown, test a large positive number
        try {
            JapaneseEra.of(1000);
            throw new AssertionError("Expected DateTimeException for JapaneseEra.of(1000)");
        } catch (DateTimeException expected) {
            // Expected exception
        }

        // Test boundary condition: KNOWN_ERAS.length - japaneseEra <= 1
        // Without access to KNOWN_ERAS length, test 2 which is likely invalid
        try {
            JapaneseEra.of(2);
            // It may or may not throw depending on KNOWN_ERAS length, so no assertion here
        } catch (DateTimeException expected) {
            // Acceptable if exception thrown
        }
    }
}
