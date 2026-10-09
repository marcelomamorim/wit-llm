/* @test
   @summary Verifies the behavior of nextClearBit method in BitSet.
   @run main BitSetWitupTest_P3155a5ba924f
 */
import java.util.BitSet;
public class BitSetWitupTest_P3155a5ba924f {
    public static void main(String[] args) {
        testNextClearBitBasic();
        testNextClearBitBeyondWordsInUse();
        testNextClearBitNegativeIndex();
    }

    private static void testNextClearBitBasic() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        int result = bs.nextClearBit(0);
        if (result != 1) {
            throw new AssertionError("Expected 1, but got " + result);
        }
        result = bs.nextClearBit(2);
        if (result != 3) {
            throw new AssertionError("Expected 3, but got " + result);
        }
    }

    private static void testNextClearBitBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        int indexBeyond = bs.wordsInUse * 64 + 10; // beyond current words in use
        int result = bs.nextClearBit(indexBeyond);
        if (result != indexBeyond) {
            throw new AssertionError("Expected " + indexBeyond + ", but got " + result);
        }
    }

    private static void testNextClearBitNegativeIndex() {
        try {
            BitSet bs = new BitSet();
            bs.nextClearBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
