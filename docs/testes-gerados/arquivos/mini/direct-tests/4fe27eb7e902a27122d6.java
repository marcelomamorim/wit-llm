/*
 * @test
 * @summary Test sun.security.util.BitArray.get(int) method
 * @run main BitArrayWitupTest
 */

public class BitArrayWitupTest {
    public static void main(String[] args) {
        testGetValidBits();
        testGetIndexOutOfBounds();
    }

    private static void testGetValidBits() {
        // Create BitArray of length 16 with known pattern
        byte[] bytes = new byte[] {(byte)0b10101010, (byte)0b01010101};
        sun.security.util.BitArray ba = new sun.security.util.BitArray(16, bytes);

        // Check bits at various positions
        // bits are big-endian in each byte, so index 0 is MSB of bytes[0]
        boolean[] expected = new boolean[] {
            true, false, true, false, true, false, true, false,
            false, true, false, true, false, true, false, true
        };

        for (int i = 0; i < 16; i++) {
            boolean bit = ba.get(i);
            if (bit != expected[i]) {
                throw new RuntimeException("Bit mismatch at index " + i + ": expected " + expected[i] + ", got " + bit);
            }
        }
    }

    private static void testGetIndexOutOfBounds() {
        sun.security.util.BitArray ba = new sun.security.util.BitArray(8);

        // Test negative index
        try {
            ba.get(-1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (!"-1".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for index -1: " + e.getMessage());
            }
        }

        // Test index equal to length
        try {
            ba.get(8);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 8");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (!"8".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for index 8: " + e.getMessage());
            }
        }

        // Test index greater than length
        try {
            ba.get(100);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 100");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (!"100".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for index 100: " + e.getMessage());
            }
        }
    }
}
