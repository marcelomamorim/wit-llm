/*
 * @test
 * @summary Test jdk.internal.math.FloatingDecimal.getDigits(char[]) throws IllegalArgumentException
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        jdk.internal.math.FloatingDecimal fd = new jdk.internal.math.FloatingDecimal();
        char[] dummy = new char[1];
        try {
            fd.getDigits(dummy);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Exceptional value does not have digits")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
