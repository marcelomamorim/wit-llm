/*
 * @test
 * @summary Checks BitSet.clear(int) negative index exception and observable clearing behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testNegativeIndexThrows();
        testNegativeIndexDoesNotChangeState();
        testClearBeyondLogicalSizeIsNoop();
        testClearOnlyTargetBit();
        testClearLastBitResetsLength();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeIndexThrows() {
        int[] indexes = {-1, -64, Integer.MIN_VALUE};
        for (int index : indexes) {
            BitSet bits = new BitSet();
            boolean thrown = false;
            try {
                bits.clear(index);
            } catch (IndexOutOfBoundsException e) {
                thrown = true;
            }
            check(thrown, "clear(" + index + ") should throw IndexOutOfBoundsException");
        }
    }

    static void testNegativeIndexDoesNotChangeState() {
        BitSet bits = new BitSet();
        bits.set(0);
        bits.set(10);
        try {
            bits.clear(-5);
            throw new RuntimeException("clear(-5) should throw");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        check(bits.get(0), "bit 0 should remain set after failed clear");
        check(bits.get(10), "bit 10 should remain set after failed clear");
        check(bits.cardinality() == 2, "cardinality should remain 2");
    }

    static void testClearBeyondLogicalSizeIsNoop() {
        BitSet bits = new BitSet();
        bits.set(3);
        bits.clear(1000);
        check(bits.get(3), "bit 3 should remain set");
        check(bits.cardinality() == 1, "cardinality should remain 1");
        check(bits.length() == 4, "length should remain 4");

        bits.clear(70);
        check(bits.get(3), "bit 3 should remain set after clearing index 70");
        check(bits.cardinality() == 1, "cardinality should still be 1");
    }

    static void testClearOnlyTargetBit() {
        BitSet bits = new BitSet();
        bits.set(0, 128);
        bits.clear(64);
        check(!bits.get(64), "bit 64 should be cleared");
        check(bits.get(63), "bit 63 should remain set");
        check(bits.get(65), "bit 65 should remain set");
        check(bits.cardinality() == 127, "cardinality should be 127");
    }

    static void testClearLastBitResetsLength() {
        BitSet bits = new BitSet();
        bits.set(100);
        bits.clear(100);
        check(bits.isEmpty(), "bit set should be empty");
        check(bits.length() == 0, "length should be 0");
    }
}
