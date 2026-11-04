/*
 * @test
 * @summary Observable behavior of BitSet iteration (forEachRemaining path reached through stream()) across word boundaries
 * @run main BitSetWitupTest
 */

import java.util.Arrays;
import java.util.BitSet;

public class BitSetWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testEmptyBitSet() {
        BitSet bs = new BitSet();
        int[] values = bs.stream().toArray();
        check(values.length == 0, "empty BitSet must yield no indices");
    }

    private static void testSparseBitsAcrossWordBoundaries() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(63);
        bs.set(64);
        bs.set(130);
        int[] expected = {0, 1, 63, 64, 130};
        int[] actual = bs.stream().toArray();
        check(Arrays.equals(expected, actual),
              "indices across word boundaries mismatch: " + Arrays.toString(actual));
    }

    private static void testIterationDoesNotModifyBitSet() {
        BitSet bs = new BitSet();
        bs.set(3);
        bs.set(70);
        bs.set(127);
        int before = bs.cardinality();
        int count = (int) bs.stream().count();
        check(count == 3, "stream must visit exactly 3 set bits, got " + count);
        check(bs.cardinality() == before, "iteration must not alter the BitSet");
        check(bs.get(3) && bs.get(70) && bs.get(127), "set bits must remain set after iteration");
    }

    private static void testFullWordAndNextWord() {
        BitSet bs = new BitSet();
        for (int i = 60; i < 68; i++) {
            bs.set(i);
        }
        int[] actual = bs.stream().toArray();
        int[] expected = {60, 61, 62, 63, 64, 65, 66, 67};
        check(Arrays.equals(expected, actual),
              "contiguous range across words mismatch: " + Arrays.toString(actual));
    }

    public static void main(String[] args) {
        testEmptyBitSet();
        testSparseBitsAcrossWordBoundaries();
        testIterationDoesNotModifyBitSet();
        testFullWordAndNextWord();
    }
}
