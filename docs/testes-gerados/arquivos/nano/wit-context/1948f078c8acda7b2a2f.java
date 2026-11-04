/*
 * @test
 * @summary Test for BitSet.clear(int) method, focusing on exception path for invalid index.
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testClearWithNegativeIndex();
        testClearWithValidIndex();
    }

    private static void testClearWithNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(-1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for negative index");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testClearWithValidIndex() {
        BitSet bs = new BitSet();
        int index = 10;
        bs.set(index);
        bs.clear(index);
        if (bs.get(index)) {
            System.err.println("Failed: Bit at index " + index + " should be cleared");
            System.exit(1);
        } else {
            System.out.println("Passed: Bit at index " + index + " successfully cleared");
        }
    }
}
