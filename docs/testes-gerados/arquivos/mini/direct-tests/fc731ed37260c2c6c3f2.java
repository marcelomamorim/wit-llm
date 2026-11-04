/*
 * @test
 * @summary Test BitSet(int nbits) constructor for valid and invalid inputs
 * @run main BitSetWitupTest
 */

public class BitSetWitupTest {
    public static void main(String[] args) {
        testValidSize();
        testZeroSize();
        testNegativeSize();
    }

    private static void testValidSize() {
        java.util.BitSet bs = new java.util.BitSet(64);
        if (bs.length() != 0) {
            throw new RuntimeException("Expected length 0 for new BitSet(64), got " + bs.length());
        }
        // Setting a bit within range should work
        bs.set(63);
        if (!bs.get(63)) {
            throw new RuntimeException("Bit 63 should be set");
        }
    }

    private static void testZeroSize() {
        java.util.BitSet bs = new java.util.BitSet(0);
        if (bs.length() != 0) {
            throw new RuntimeException("Expected length 0 for new BitSet(0), got " + bs.length());
        }
        // Setting bit 0 should expand the BitSet
        bs.set(0);
        if (!bs.get(0)) {
            throw new RuntimeException("Bit 0 should be set after set(0)");
        }
    }

    private static void testNegativeSize() {
        try {
            new java.util.BitSet(-1);
            throw new RuntimeException("Expected NegativeArraySizeException for new BitSet(-1)");
        } catch (NegativeArraySizeException e) {
            // Expected
        }
    }
}
