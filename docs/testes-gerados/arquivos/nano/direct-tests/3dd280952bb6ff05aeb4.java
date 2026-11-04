/* @test    @summary Test parseFloat method of FloatingDecimal class    @run main FloatingDecimalWitupTest */

import java.util.regex.*;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testValidFloatStrings();
        testInvalidFloatStrings();
        testEdgeCases();
    }

    static void testValidFloatStrings() {
        String[] validStrings = {"0.0", "1.0", "-1.0", "3.4028235e+38", "-3.4028235e+38", "1.4E-45"};
        for (String s : validStrings) {
            float result = 0.0f;
            try {
                result = jdk.internal.math.FloatingDecimal.parseFloat(s);
                System.out.println("parseFloat(\"" + s + "\") = " + result);
            } catch (NumberFormatException e) {
                throw new AssertionError("Expected valid float string: " + s, e);
            }
        }
    }

    static void testInvalidFloatStrings() {
        String[] invalidStrings = {"abc", "1.2.3", "", "NaNNaN", "1e309", "-1e309"};
        for (String s : invalidStrings) {
            try {
                jdk.internal.math.FloatingDecimal.parseFloat(s);
                throw new AssertionError("Expected NumberFormatException for input: " + s);
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }

    static void testEdgeCases() {
        String[] edgeCases = {"-0.0", "+0.0", "Infinity", "-Infinity", "NaN"};
        for (String s : edgeCases) {
            try {
                float val = jdk.internal.math.FloatingDecimal.parseFloat(s);
                System.out.println("parseFloat(\"" + s + "\") = " + val);
            } catch (NumberFormatException e) {
                // For "Infinity", "-Infinity", and "NaN", parseFloat may throw, but if it does, treat as failure
                if (s.equals("Infinity") || s.equals("-Infinity") || s.equals("NaN")) {
                    throw new AssertionError("Unexpected exception for edge case: " + s, e);
                }
            }
        }
    }
}
