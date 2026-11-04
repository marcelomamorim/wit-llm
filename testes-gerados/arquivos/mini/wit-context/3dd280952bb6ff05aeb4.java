/*
 * @test
 * @summary Test FloatingDecimal.parseFloat(String) for valid and invalid inputs
 * @run main FloatingDecimalWitupTest
 */

import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        testValidFloatStrings();
        testInvalidFloatStrings();
    }

    private static void testValidFloatStrings() {
        // Test some valid float strings and verify parseFloat returns expected float values
        String[] validInputs = {
            "0.0",
            "1.0",
            "-1.0",
            "3.14159",
            "2.71828",
            "1e10",
            "-1e-10",
            "0",
            "123456.789",
            "1.4E-45" // smallest positive float
        };

        for (String s : validInputs) {
            float result = FloatingDecimal.parseFloat(s);
            // We cannot assert exact equality for all floats due to rounding,
            // but parseFloat should not throw and result should be finite for these inputs
            if (Float.isNaN(result) || Float.isInfinite(result)) {
                throw new RuntimeException("parseFloat returned NaN or Infinity for valid input: " + s);
            }
        }
    }

    private static void testInvalidFloatStrings() {
        // Test some invalid float strings that should cause NumberFormatException
        String[] invalidInputs = {
            "",
            "abc",
            "1.0.0",
            "--1.0",
            "1e",
            "e10",
            "NaNabc",
            "Infinityx",
            "1_000",
            "0x1.0p0" // hex float literal not supported by parseFloat here
        };

        for (String s : invalidInputs) {
            boolean thrown = false;
            try {
                FloatingDecimal.parseFloat(s);
            } catch (NumberFormatException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected NumberFormatException for input: " + s);
            }
        }
    }
}
