/* @test
   @summary Testa o método nextClearBit de java.util.BitSet.
   @run main BitSetWitupTest_P3155a5ba924f
 */
import java.util.*;
public class BitSetWitupTest_P3155a5ba924f {
    public static void main(String[] args) {
        testNextClearBitNegativeIndex();
        testNextClearBitIndexBeyondWordsInUse();
        testNextClearBitWithinWords();
        testNextClearBitAtMaxIndex();
    }

    static void testNextClearBitNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextClearBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNextClearBitIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        int index = bs.nextClearBit(100);
        if (index != 100) {
            throw new AssertionError("Expected next clear bit at 100, got " + index);
        }
    }

    static void testNextClearBitWithinWords() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        int index = bs.nextClearBit(0);
        if (index != 1) {
            throw new AssertionError("Expected next clear bit at 1, got " + index);
        }
    }

    static void testNextClearBitAtMaxIndex() {
        BitSet bs = new BitSet();
        int maxIndex = Integer.MAX_VALUE - 1;
        int index = bs.nextClearBit(maxIndex);
        if (index != maxIndex) {
            throw new AssertionError("Expected next clear bit at " + maxIndex + ", got " + index);
        }
    }
}
