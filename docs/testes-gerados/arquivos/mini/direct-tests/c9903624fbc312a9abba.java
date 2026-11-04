/*
 * @test
 * @summary Test java.util.BitSet.set(int) method behavior and exceptions
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testSetValidBit();
        testSetZeroBit();
        testSetNegativeBitThrows();
    }

    private static void testSetValidBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        if (!bs.get(5)) {
            throw new RuntimeException("Bit 5 should be set");
        }
        // Check that other bits are not set
        if (bs.get(4) || bs.get(6)) {
            throw new RuntimeException("Bits other than 5 should not be set");
        }
    }

    private static void testSetZeroBit() {
        BitSet bs = new BitSet();
        bs.set(0);
        if (!bs.get(0)) {
            throw new RuntimeException("Bit 0 should be set");
        }
    }

    private static void testSetNegativeBitThrows() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative bitIndex");
        } catch (IndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("bitIndex < 0")) {
                throw new RuntimeException("Exception message should mention 'bitIndex < 0'");
            }
        }
    }
}
