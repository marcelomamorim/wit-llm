/* @test
   @summary Verifies the behavior of BitSet.nextSetBit method.
   @run main BitSetWitupTest_Pc8c3dab2b12b
 */
import java.util.BitSet;
public class BitSetWitupTest_Pc8c3dab2b12b {
    public static void main(String[] args) {
        testNextSetBitWithSetBits();
        testNextSetBitWithNoSetBits();
        testNextSetBitWithNegativeFromIndex();
        testNextSetBitFromIndexBeyondSize();
    }

    private static void testNextSetBitWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(5);
        bs.set(10);
        int result1 = bs.nextSetBit(0);
        int result2 = bs.nextSetBit(3);
        int result3 = bs.nextSetBit(6);
        int result4 = bs.nextSetBit(11);
        if (result1 != 2) {
            throw new AssertionError("Expected 2, got " + result1);
        }
        if (result2 != 5) {
            throw new AssertionError("Expected 5, got " + result2);
        }
        if (result3 != 10) {
            throw new AssertionError("Expected 10, got " + result3);
        }
        if (result4 != -1) {
            throw new AssertionError("Expected -1, got " + result4);
        }
    }

    private static void testNextSetBitWithNoSetBits() {
        BitSet bs = new BitSet();
        int result = bs.nextSetBit(0);
        if (result != -1) {
            throw new AssertionError("Expected -1, got " + result);
        }
    }

    private static void testNextSetBitWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextSetBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testNextSetBitFromIndexBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        int result = bs.nextSetBit(100);
        if (result != -1) {
            throw new AssertionError("Expected -1, got " + result);
        }
    }
}
