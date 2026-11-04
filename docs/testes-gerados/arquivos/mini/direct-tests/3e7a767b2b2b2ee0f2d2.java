/*
 * @test
 * @summary Test digitsRoundedUp method throws IllegalArgumentException
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        jdk.internal.math.FloatingDecimal fd = new jdk.internal.math.FloatingDecimal();
        try {
            fd.digitsRoundedUp();
            throw new Error("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            if (!"Exceptional value is not rounded".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
