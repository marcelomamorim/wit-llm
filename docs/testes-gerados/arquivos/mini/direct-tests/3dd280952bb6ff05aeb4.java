/*
 * @test
 * @summary Test jdk.internal.math.FloatingDecimal.parseFloat(String) for valid and invalid inputs
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        // Valid parse
        String valid1 = "123.456";
        float f1 = jdk.internal.math.FloatingDecimal.parseFloat(valid1);
        if (Float.compare(f1, 123.456f) != 0) {
            throw new RuntimeException("Failed to parse valid float string: " + valid1 + ", got: " + f1);
        }

        // Valid parse with exponent
        String valid2 = "1.23e2";
        float f2 = jdk.internal.math.FloatingDecimal.parseFloat(valid2);
        if (Float.compare(f2, 123f) != 0) {
            throw new RuntimeException("Failed to parse valid float string with exponent: " + valid2 + ", got: " + f2);
        }

        // Valid parse with leading and trailing spaces
        String valid3 = "  0.5  ";
        float f3 = jdk.internal.math.FloatingDecimal.parseFloat(valid3.trim());
        if (Float.compare(f3, 0.5f) != 0) {
            throw new RuntimeException("Failed to parse valid float string with spaces: " + valid3 + ", got: " + f3);
        }

        // Valid parse of special values
        String valid4 = "NaN";
        float f4 = jdk.internal.math.FloatingDecimal.parseFloat(valid4);
        if (!Float.isNaN(f4)) {
            throw new RuntimeException("Failed to parse NaN string: " + valid4 + ", got: " + f4);
        }

        String valid5 = "Infinity";
        float f5 = jdk.internal.math.FloatingDecimal.parseFloat(valid5);
        if (f5 != Float.POSITIVE_INFINITY) {
            throw new RuntimeException("Failed to parse Infinity string: " + valid5 + ", got: " + f5);
        }

        String valid6 = "-Infinity";
        float f6 = jdk.internal.math.FloatingDecimal.parseFloat(valid6);
        if (f6 != Float.NEGATIVE_INFINITY) {
            throw new RuntimeException("Failed to parse -Infinity string: " + valid6 + ", got: " + f6);
        }

        // Invalid parse - empty string
        try {
            jdk.internal.math.FloatingDecimal.parseFloat("");
            throw new RuntimeException("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }

        // Invalid parse - non-numeric string
        try {
            jdk.internal.math.FloatingDecimal.parseFloat("abc");
            throw new RuntimeException("Expected NumberFormatException for non-numeric string");
        } catch (NumberFormatException e) {
            // expected
        }

        // Invalid parse - malformed number
        try {
            jdk.internal.math.FloatingDecimal.parseFloat("1.2.3");
            throw new RuntimeException("Expected NumberFormatException for malformed number string");
        } catch (NumberFormatException e) {
            // expected
        }

        // Invalid parse - null input
        try {
            jdk.internal.math.FloatingDecimal.parseFloat(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
