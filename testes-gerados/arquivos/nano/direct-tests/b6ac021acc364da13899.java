/* @test    @summary Test for FloatingDecimal.decimalDigitsExact() method to verify exception handling and behavior.    @run main FloatingDecimalWitupTest */

import java.lang.IllegalArgumentException;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testDecimalDigitsExactThrowsException();
    }

    public static void testDecimalDigitsExactThrowsException() {
        boolean exceptionThrown = false;
        try {
            boolean result = jdk.internal.math.FloatingDecimal.decimalDigitsExact();
            // Since the method always throws, this line should not be reached.
            System.out.println("Unexpected result: " + result);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            System.out.println("Caught expected IllegalArgumentException: " + e.getMessage());
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        }
    }
}
