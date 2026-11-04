/* @test    @summary Test the set(int, int) method of BitSet for various ranges    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testSetEmptyRange();
        testSetSingleBit();
        testSetMultipleBitsSameWord();
        testSetMultipleWords();
        testSetFullRange();
    }

    static void testSetEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(10, 10);
        boolean isSet = bs.get(10);
        if (isSet) {
            throw new AssertionError("Expected bit 10 to be unset after set(10,10)");
        }
    }

    static void testSetSingleBit() {
        BitSet bs = new BitSet();
        bs.set(5, 6);
        if (!bs.get(5)) {
            throw new AssertionError("Bit 5 should be set");
        }
        if (bs.get(6)) {
            throw new AssertionError("Bit 6 should be unset");
        }
    }

    static void testSetMultipleBitsSameWord() {
        BitSet bs = new BitSet();
        bs.set(2, 5); // bits 2,3,4
        for (int i = 2; i < 5; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be set");
            }
        }
        if (bs.get(5)) {
            throw new AssertionError("Bit 5 should be unset");
        }
    }

    static void testSetMultipleWords() {
        BitSet bs = new BitSet();
        int start = 60;
        int end = 130;
        bs.set(start, end);
        for (int i = start; i < end; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be set");
            }
        }
        if (bs.get(end)) {
            throw new AssertionError("Bit " + end + " should be unset");
        }
    }

    static void testSetFullRange() {
        BitSet bs = new BitSet();
        int from = 0;
        int to = 64;
        bs.set(from, to);
        for (int i = from; i < to; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be set");
            }
        }
    }
}
