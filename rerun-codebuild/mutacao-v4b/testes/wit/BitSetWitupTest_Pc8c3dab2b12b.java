/* @test
   @summary Testa o método nextSetBit de java.util.BitSet para comportamentos observáveis.
   @run main BitSetWitupTest_Pc8c3dab2b12b
 */
import java.util.BitSet;
public class BitSetWitupTest_Pc8c3dab2b12b {
    public static void main(String[] args) {
        testNextSetBitNegativeIndex();
        testNextSetBitEmptyBitSet();
        testNextSetBitWithSetBits();
        testNextSetBitBeyondWords();
    }

    private static void testNextSetBitNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextSetBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testNextSetBitEmptyBitSet() {
        BitSet bs = new BitSet();
        int result = bs.nextSetBit(0);
        if (result != -1) {
            throw new AssertionError("Expected -1 for empty BitSet, got " + result);
        }
    }

    private static void testNextSetBitWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(10);
        bs.set(70);
        int r1 = bs.nextSetBit(0);
        if (r1 != 2) {
            throw new AssertionError("Expected 2, got " + r1);
        }
        int r2 = bs.nextSetBit(3);
        if (r2 != 10) {
            throw new AssertionError("Expected 10, got " + r2);
        }
        int r3 = bs.nextSetBit(11);
        if (r3 != 70) {
            throw new AssertionError("Expected 70, got " + r3);
        }
        int r4 = bs.nextSetBit(71);
        if (r4 != -1) {
            throw new AssertionError("Expected -1, got " + r4);
        }
    }

    private static void testNextSetBitBeyondWords() {
        BitSet bs = new BitSet();
        int maxIndex = 1024 * 1024; // 1 million
        bs.set(maxIndex);
        int result = bs.nextSetBit(0);
        if (result != maxIndex) {
            throw new AssertionError("Expected " + maxIndex + ", got " + result);
        }
        int after = bs.nextSetBit(maxIndex + 1);
        if (after != -1) {
            throw new AssertionError("Expected -1 after set bit at " + maxIndex + ", got " + after);
        }
    }
}
