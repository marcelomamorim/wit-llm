/* @test
   @summary Test the get(int fromIndex, int toIndex) method of BitSet.
   @run main BitSetWitupTest_P6110955bda5c
 */
import java.util.BitSet;
public class BitSetWitupTest_P6110955bda5c {
    public static void main(String[] args) {
        testEmptyRange();
        testRangeWithinBits();
        testRangeExceedsLength();
        testSingleBit();
        testFullRange();
    }

    static void testEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(10);
        BitSet result = bs.get(2, 2);
        if (result.length() != 0) {
            throw new AssertionError("Expected empty BitSet for zero-length range");
        }
    }

    static void testRangeWithinBits() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(3);
        bs.set(5);
        BitSet result = bs.get(1, 6);
        if (!result.get(0) || !result.get(1) || !result.get(2)) {
            throw new AssertionError("Expected bits 1,3,5 in result");
        }
        if (result.length() != 3) {
            throw new AssertionError("Result length mismatch");
        }
    }

    static void testRangeExceedsLength() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        BitSet result = bs.get(0, 10);
        if (!result.get(0) || !result.get(2)) {
            throw new AssertionError("Expected bits 0 and 2 in result");
        }
        if (result.length() != 3) {
            throw new AssertionError("Result length should be 3");
        }
    }

    static void testSingleBit() {
        BitSet bs = new BitSet();
        bs.set(4);
        BitSet result = bs.get(4, 5);
        if (!result.get(0)) {
            throw new AssertionError("Expected bit 0 in result");
        }
        if (result.length() != 1) {
            throw new AssertionError("Result length should be 1");
        }
    }

    static void testFullRange() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 20; i++) {
            bs.set(i);
        }
        BitSet result = bs.get(0, 20);
        for (int i = 0; i < 20; i++) {
            if (!result.get(i)) {
                throw new AssertionError("Expected bit " + i + " to be set");
            }
        }
        if (result.length() != 20) {
            throw new AssertionError("Result length mismatch");
        }
    }
}
