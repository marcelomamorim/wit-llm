/*
 * @test
 * @summary Test BitSet.flip(int) method for correctness and boundary conditions
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testFlipBasic();
        testFlipTwice();
        testFlipNegativeIndex();
        testFlipExpandsWordsInUse();
    }

    private static void testFlipBasic() {
        BitSet bs = new BitSet();
        bs.flip(0);
        if (!bs.get(0)) {
            throw new RuntimeException("flip(0) should set bit 0 to true");
        }
        bs.flip(0);
        if (bs.get(0)) {
            throw new RuntimeException("flip(0) twice should clear bit 0");
        }
    }

    private static void testFlipTwice() {
        BitSet bs = new BitSet();
        int bit = 10;
        bs.flip(bit);
        if (!bs.get(bit)) {
            throw new RuntimeException("flip(bit) should set bit to true");
        }
        bs.flip(bit);
        if (bs.get(bit)) {
            throw new RuntimeException("flip(bit) twice should clear bit");
        }
    }

    private static void testFlipNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.flip(-1);
            throw new RuntimeException("flip(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testFlipExpandsWordsInUse() {
        BitSet bs = new BitSet();
        int bitIndex = 1000; // large bit index to force expansion
        bs.flip(bitIndex);
        if (!bs.get(bitIndex)) {
            throw new RuntimeException("flip(bitIndex) should set bit at large index");
        }
        // flip again to clear
        bs.flip(bitIndex);
        if (bs.get(bitIndex)) {
            throw new RuntimeException("flip(bitIndex) twice should clear bit");
        }
    }
}
