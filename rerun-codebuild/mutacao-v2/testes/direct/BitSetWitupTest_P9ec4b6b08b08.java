/*
 * @test
 * @summary Test java.util.BitSet.clear(int fromIndex, int toIndex) method
 * @run main BitSetWitupTest_P9ec4b6b08b08
 */

import java.util.BitSet;

public class BitSetWitupTest_P9ec4b6b08b08 {
    public static void main(String[] args) {
        testClearEmptyRange();
        testClearSingleWordRange();
        testClearMultipleWordsRange();
        testClearRangeBeyondWordsInUse();
        testClearNoEffectWhenFromEqualsTo();
    }

    private static void testClearEmptyRange() {
        BitSet bs = new BitSet(64);
        bs.set(10);
        bs.clear(20, 20); // empty range, should do nothing
        if (!bs.get(10)) {
            throw new RuntimeException("testClearEmptyRange failed: bit 10 should remain set");
        }
    }

    private static void testClearSingleWordRange() {
        BitSet bs = new BitSet(64);
        bs.set(5);
        bs.set(10);
        bs.set(15);
        bs.clear(6, 14); // should clear bit 10 only
        if (bs.get(10)) {
            throw new RuntimeException("testClearSingleWordRange failed: bit 10 should be cleared");
        }
        if (!bs.get(5) || !bs.get(15)) {
            throw new RuntimeException("testClearSingleWordRange failed: bits 5 and 15 should remain set");
        }
    }

    private static void testClearMultipleWordsRange() {
        BitSet bs = new BitSet(130); // at least 3 words (64*2=128 bits)
        bs.set(10);
        bs.set(70);
        bs.set(129);
        bs.clear(5, 128); // should clear bits 10 and 70, but not 129
        if (bs.get(10) || bs.get(70)) {
            throw new RuntimeException("testClearMultipleWordsRange failed: bits 10 and 70 should be cleared");
        }
        if (!bs.get(129)) {
            throw new RuntimeException("testClearMultipleWordsRange failed: bit 129 should remain set");
        }
    }

    private static void testClearRangeBeyondWordsInUse() {
        BitSet bs = new BitSet(64);
        bs.set(10);
        bs.clear(5, 100); // toIndex beyond wordsInUse, should clear bit 10
        if (bs.get(10)) {
            throw new RuntimeException("testClearRangeBeyondWordsInUse failed: bit 10 should be cleared");
        }
    }

    private static void testClearNoEffectWhenFromEqualsTo() {
        BitSet bs = new BitSet(64);
        bs.set(10);
        bs.clear(10, 10); // no effect
        if (!bs.get(10)) {
            throw new RuntimeException("testClearNoEffectWhenFromEqualsTo failed: bit 10 should remain set");
        }
    }
}
