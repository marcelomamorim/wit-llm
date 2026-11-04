/*
 * @test
 * @summary Test BitSet.flip(int) method for valid and invalid inputs
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testFlipValidBit();
        testFlipZeroBit();
        testFlipNegativeBitThrows();
        System.out.println("All tests passed.");
    }

    private static void testFlipValidBit() {
        BitSet bs = new BitSet();
        // Flip bit 5 (initially false)
        bs.flip(5);
        if (!bs.get(5)) {
            throw new AssertionError("Bit 5 should be set after flip");
        }
        // Flip bit 5 again (should clear it)
        bs.flip(5);
        if (bs.get(5)) {
            throw new AssertionError("Bit 5 should be cleared after second flip");
        }
    }

    private static void testFlipZeroBit() {
        BitSet bs = new BitSet();
        // Flip bit 0 (boundary case)
        bs.flip(0);
        if (!bs.get(0)) {
            throw new AssertionError("Bit 0 should be set after flip");
        }
    }

    private static void testFlipNegativeBitThrows() {
        BitSet bs = new BitSet();
        try {
            bs.flip(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative bitIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("bitIndex < 0")) {
                throw new AssertionError("Exception message should mention 'bitIndex < 0', but was: " + msg);
            }
        }
    }
}
