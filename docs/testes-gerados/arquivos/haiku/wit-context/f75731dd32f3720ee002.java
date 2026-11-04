/*
 * @test
 * @summary Witup-generated tests for BitSet.get(int): negative index rejection and observable behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeIndexThrows();
        testMinValueIndexThrows();
        testNewBitSetReturnsFalse();
        testSetBitIsObservable();
        testIndexBeyondSizeReturnsFalse();
        testWordBoundaryBits();
    }

    static void testNegativeIndexThrows() {
        BitSet bs = new BitSet();
        bs.set(0);
        try {
            bs.get(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for get(-1)");
        } catch (IndexOutOfBoundsException e) {
            if (e.getMessage() == null || !e.getMessage().contains("bitIndex < 0")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testMinValueIndexThrows() {
        BitSet bs = new BitSet(64);
        try {
            bs.get(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IndexOutOfBoundsException for Integer.MIN_VALUE");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNewBitSetReturnsFalse() {
        BitSet bs = new BitSet();
        if (bs.get(0)) {
            throw new RuntimeException("New BitSet bit 0 should be false");
        }
        if (bs.get(1000)) {
            throw new RuntimeException("New BitSet bit 1000 should be false");
        }
    }

    static void testSetBitIsObservable() {
        BitSet bs = new BitSet();
        bs.set(5);
        if (!bs.get(5)) {
            throw new RuntimeException("Bit 5 should be true after set(5)");
        }
        if (bs.get(4) || bs.get(6)) {
            throw new RuntimeException("Neighbouring bits should remain false");
        }
        bs.clear(5);
        if (bs.get(5)) {
            throw new RuntimeException("Bit 5 should be false after clear(5)");
        }
    }

    static void testIndexBeyondSizeReturnsFalse() {
        BitSet bs = new BitSet(8);
        bs.set(3);
        if (bs.get(Integer.MAX_VALUE)) {
            throw new RuntimeException("Index beyond logical size should return false");
        }
        if (bs.get(10_000)) {
            throw new RuntimeException("Large index beyond size should return false");
        }
    }

    static void testWordBoundaryBits() {
        BitSet bs = new BitSet();
        bs.set(63);
        bs.set(64);
        if (!bs.get(63) || !bs.get(64)) {
            throw new RuntimeException("Bits 63 and 64 should be true");
        }
        if (bs.get(62) || bs.get(65)) {
            throw new RuntimeException("Bits 62 and 65 should be false");
        }
    }
}
