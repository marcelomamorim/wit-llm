/*
 * @test
 * @summary Tests for MethodType.genericMethodType(int)
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        testValidArgument();
        testInvalidArgument();
    }

    private static void testValidArgument() {
        int validArgCount = 2;
        try {
            MethodType mt = MethodType.genericMethodType(validArgCount);
            if (mt == null) {
                throw new AssertionError("MethodType.genericMethodType returned null for valid input");
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for valid input", t);
        }
    }

    private static void testInvalidArgument() {
        int invalidArgCount = 256; // (256 & 255) != 256, should trigger exception
        try {
            MethodType.genericMethodType(invalidArgCount);
            throw new AssertionError("Expected IllegalArgumentException for invalid input");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type for invalid input", t);
        }
    }
}
