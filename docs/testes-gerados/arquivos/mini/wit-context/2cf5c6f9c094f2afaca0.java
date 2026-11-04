/*
 * @test
 * @summary Test java.util.BitSet.clear(int fromIndex, int toIndex) for boundary and exception cases
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        BitSet bs = new BitSet(64);

        // Normal clear range
        bs.set(10);
        bs.set(20);
        bs.clear(10, 21);
        if (bs.get(10) || bs.get(20)) {
            throw new RuntimeException("Failed to clear bits in range");
        }

        // Clear empty range (fromIndex == toIndex) should do nothing
        bs.set(30);
        bs.clear(30, 30);
        if (!bs.get(30)) {
            throw new RuntimeException("clear with empty range cleared bits unexpectedly");
        }

        // Clear range beyond wordsInUse should adjust to length
        bs.set(63);
        bs.clear(60, 1000); // toIndex > length
        if (bs.get(63)) {
            throw new RuntimeException("Failed to clear bits up to length when toIndex > length");
        }

        // Exception cases: check that IndexOutOfBoundsException is thrown for invalid ranges
        // fromIndex < 0
        try {
            bs.clear(-1, 5);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // toIndex < 0 and fromIndex >= 0
        try {
            bs.clear(0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for toIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // fromIndex > toIndex
        try {
            bs.clear(10, 5);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
