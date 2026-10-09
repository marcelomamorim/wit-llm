/* @test    @summary Test for BitSet.nextSetBit(int fromIndex) method.    @run main BitSetWitupTest_P733d11c181a5 */

import java.util.BitSet;

public class BitSetWitupTest_P733d11c181a5 {
    public static void main(String[] args) {
        testNextSetBitWithExplicitBits();
        testNextSetBitWithEmptyBitSet();
        testNextSetBitWithNegativeFromIndex();
        testNextSetBitWithFromIndexBeyondSize();
    }

    static void testNextSetBitWithExplicitBits() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(5);
        bs.set(10);
        int res1 = bs.nextSetBit(0);
        int res2 = bs.nextSetBit(3);
        int res3 = bs.nextSetBit(6);
        int res4 = bs.nextSetBit(11);
        int res5 = bs.nextSetBit(10);
        assert res1 == 2 : "Expected 2, got " + res1;
        assert res2 == 5 : "Expected 5, got " + res2;
        assert res3 == 10 : "Expected 10, got " + res3;
        assert res4 == -1 : "Expected -1, got " + res4;
        assert res5 == 10 : "Expected 10, got " + res5;
    }

    static void testNextSetBitWithEmptyBitSet() {
        BitSet bs = new BitSet();
        int res = bs.nextSetBit(0);
        assert res == -1 : "Expected -1, got " + res;
    }

    static void testNextSetBitWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(3);
        try {
            bs.nextSetBit(-1);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNextSetBitWithFromIndexBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(4);
        bs.set(8);
        int res = bs.nextSetBit(20);
        assert res == -1 : "Expected -1, got " + res;
    }
}
