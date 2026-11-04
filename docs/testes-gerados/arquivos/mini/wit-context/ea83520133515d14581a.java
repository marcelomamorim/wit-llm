/*
 * @test
 * @summary Test MethodType.genericMethodType(int) for valid and invalid argument counts
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {
    public static void main(String[] args) {
        testValidCounts();
        testInvalidCounts();
    }

    private static void testValidCounts() {
        // Test some valid counts within 0..255
        for (int i = 0; i <= 10; i++) {
            MethodType mt = MethodType.genericMethodType(i);
            if (mt == null) {
                throw new AssertionError("genericMethodType returned null for count " + i);
            }
        }
        // Test boundary values
        MethodType mt0 = MethodType.genericMethodType(0);
        if (mt0 == null) throw new AssertionError("genericMethodType returned null for 0");
        MethodType mt255 = MethodType.genericMethodType(255);
        if (mt255 == null) throw new AssertionError("genericMethodType returned null for 255");
    }

    private static void testInvalidCounts() {
        // According to WIT expath, counts outside 0..255 should throw IllegalArgumentException
        int[] invalidCounts = { -1, 256, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int count : invalidCounts) {
            try {
                MethodType.genericMethodType(count);
                throw new AssertionError("Expected IllegalArgumentException for count: " + count);
            } catch (IllegalArgumentException e) {
                // Expected
            } catch (Throwable t) {
                throw new AssertionError("Unexpected exception for count " + count + ": " + t);
            }
        }
    }
}
