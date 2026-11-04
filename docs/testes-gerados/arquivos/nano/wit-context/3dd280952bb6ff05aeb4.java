/*
 * @test
 * @summary Tests for jdk.internal.math.FloatingDecimal.parseFloat(String s)
 * @run main FloatingDecimalWitupTest
 */

import java.io.StringWriter;
import java.io.Writer;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testInvalidInput();
    }

    static void testValidInput() {
        String[] validInputs = {"0.0", "1.23", "-4.56", "7E10", "-3.14"};
        for (String input : validInputs) {
            try {
                float result = jdk.internal.math.FloatingDecimal.parseFloat(input);
                System.out.println("parseFloat(\"" + input + "\") = " + result);
            } catch (NumberFormatException e) {
                System.err.println("Unexpected NumberFormatException for input: " + input);
            }
        }
    }

    static void testInvalidInput() {
        String[] invalidInputs = {"abc", "1.2.3", "", "NaN", "Infinity", "-Infinity"};
        for (String input : invalidInputs) {
            try {
                float result = jdk.internal.math.FloatingDecimal.parseFloat(input);
                System.err.println("Expected exception but got result: " + result + " for input: " + input);
            } catch (NumberFormatException e) {
                System.out.println("Correctly threw NumberFormatException for input: " + input);
            }
        }
    }
}
