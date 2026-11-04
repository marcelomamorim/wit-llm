/*
 * @test
 * @summary Test BitArray(int length) constructor for valid and invalid inputs
 * @run main BitArrayWitupTest
 */

import sun.security.util.BitArray;

public class BitArrayWitupTest {
    public static void main(String[] args) {
        testValidLengths();
        testNegativeLength();
    }

    private static void testValidLengths() {
        // Test zero length
        BitArray ba0 = new BitArray(0);
        if (ba0 == null) throw new RuntimeException("BitArray constructor returned null for length 0");

        // Test small positive length
        BitArray ba1 = new BitArray(1);
        if (ba1 == null) throw new RuntimeException("BitArray constructor returned null for length 1");

        // Test length exactly multiple of 8
        BitArray ba8 = new BitArray(8);
        if (ba8 == null) throw new RuntimeException("BitArray constructor returned null for length 8");

        // Test length not multiple of 8
        BitArray ba10 = new BitArray(10);
        if (ba10 == null) throw new RuntimeException("BitArray constructor returned null for length 10");
    }

    private static void testNegativeLength() {
        try {
            new BitArray(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative length not thrown");
        } catch (IllegalArgumentException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative length")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
