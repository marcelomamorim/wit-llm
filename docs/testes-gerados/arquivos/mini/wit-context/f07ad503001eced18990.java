/*
 * @test
 * @summary Test FloatingDecimal.getDigits throws IllegalArgumentException
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        testGetDigitsThrows();
    }

    private static void testGetDigitsThrows() {
        char[] dummy = new char[10];
        try {
            // The method is expected to throw IllegalArgumentException unconditionally
            jdk.internal.math.FloatingDecimal fd = new jdk.internal.math.FloatingDecimal();
            fd.getDigits(dummy);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Exceptional value")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
    }
}
