/*
 * @test
 * @summary Test jdk.internal.math.FloatingDecimal.digitsRoundedUp() throws IllegalArgumentException
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        try {
            // The method digitsRoundedUp() always throws IllegalArgumentException
            new jdk.internal.math.FloatingDecimal() {
                @Override
                public boolean digitsRoundedUp() {
                    return super.digitsRoundedUp();
                }
            }.digitsRoundedUp();
            throw new Error("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Exceptional value is not rounded")) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }
}
