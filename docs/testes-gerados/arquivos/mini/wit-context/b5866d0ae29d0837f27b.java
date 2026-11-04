/*
 * @test
 * @summary Test FloatingDecimal.getDecimalExponent() throws IllegalArgumentException
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        try {
            // The method getDecimalExponent() always throws IllegalArgumentException
            // as per current source code.
            new jdk.internal.math.FloatingDecimal().getDecimalExponent();
            throw new Error("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
            String msg = e.getMessage();
            if (msg == null || !msg.contains("exponent")) {
                throw new Error("Exception message unexpected: " + msg);
            }
        } catch (Throwable t) {
            throw new Error("Unexpected exception type: " + t);
        }
    }
}
