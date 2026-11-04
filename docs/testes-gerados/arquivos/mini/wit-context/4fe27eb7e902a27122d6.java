/*
 * @test
 * @summary Test sun.security.util.BitArray.get(int) for valid and invalid indices
 * @run main BitArrayWitupTest
 */

public class BitArrayWitupTest {
    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndexNegative();
        testGetInvalidIndexTooLarge();
    }

    private static void testGetValidIndex() {
        // Create BitArray of length 16, all bits false initially
        sun.security.util.BitArray ba = new sun.security.util.BitArray(16);
        // Set bit at index 5 to true by creating boolean array
        boolean[] bits = new boolean[16];
        bits[5] = true;
        ba = new sun.security.util.BitArray(bits);

        // Check bit at index 5 is true
        if (!ba.get(5)) {
            throw new RuntimeException("Expected bit at index 5 to be true");
        }

        // Check bit at index 0 is false
        if (ba.get(0)) {
            throw new RuntimeException("Expected bit at index 0 to be false");
        }

        // Check bit at last valid index (15) is false
        if (ba.get(15)) {
            throw new RuntimeException("Expected bit at index 15 to be false");
        }
    }

    private static void testGetInvalidIndexNegative() {
        sun.security.util.BitArray ba = new sun.security.util.BitArray(10);
        try {
            ba.get(-1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
            if (!"-1".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for index -1");
            }
        }
    }

    private static void testGetInvalidIndexTooLarge() {
        sun.security.util.BitArray ba = new sun.security.util.BitArray(10);
        try {
            ba.get(10); // index == length
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 10");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
            if (!"10".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for index 10");
            }
        }

        try {
            ba.get(100); // index > length
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 100");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
            if (!"100".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for index 100");
            }
        }
    }
}
