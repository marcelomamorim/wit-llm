/*
 * @test
 * @summary Test FloatingDecimal.decimalDigitsExact() throws IllegalArgumentException
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        try {
            // The method decimalDigitsExact() always throws IllegalArgumentException
            new jdk.internal.math.FloatingDecimal.ExceptionalBinaryToASCIIBuffer().decimalDigitsExact();
            throw new Error("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("Exceptional value is not exact")) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }
}
