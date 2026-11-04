/*
 * @test
 * @summary Test getDecimalExponent() throws IllegalArgumentException
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        try {
            new jdk.internal.math.FloatingDecimal().getDecimalExponent();
            throw new Error("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Exceptional value")) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }
}
