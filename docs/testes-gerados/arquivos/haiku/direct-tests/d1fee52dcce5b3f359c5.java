/*
 * @test
 * @summary Deterministic tests for BitSet.previousSetBit covering empty sets, word boundaries, valueOf input, invalid indices and brute-force comparison
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testEmptySet();
        testSingleBit();
        testWordBoundaries();
        testValueOfLongArray();
        testNegativeIndices();
        testAgainstBruteForce();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static int bruteForcePrevious(BitSet bs, int from) {
        for (int j = from; j >= 0; j--) {
            if (bs.get(j)) {
                return j;
            }
        }
        return -1;
    }

    static void testEmptySet() {
        BitSet bs = new BitSet();
        check(bs.previousSetBit(0) == -1, "empty set, index 0");
        check(bs.previousSetBit(1000) == -1, "empty set, large index");
    }

    static void testSingleBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        check(bs.previousSetBit(5) == 5, "bit 5 should be found from 5");
        check(bs.previousSetBit(4) == -1, "no set bit at or below 4");
        check(bs.previousSetBit(1000) == 5, "search beyond size should return 5");
    }

    static void testWordBoundaries() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(63);
        bs.set(64);
        bs.set(130);
        check(bs.previousSetBit(129) == 64, "from 129 expected 64");
        check(bs.previousSetBit(130) == 130, "from 130 expected 130");
        check(bs.previousSetBit(128) == 64, "from 128 expected 64");
        check(bs.previousSetBit(65) == 64, "from 65 expected 64");
        check(bs.previousSetBit(64) == 64, "from 64 expected 64");
        check(bs.previousSetBit(63) == 63, "from 63 expected 63");
        check(bs.previousSetBit(62) == 0, "from 62 expected 0");
        check(bs.previousSetBit(1) == 0, "from 1 expected 0");
        check(bs.previousSetBit(0) == 0, "from 0 expected 0");
    }

    static void testValueOfLongArray() {
        BitSet bs = BitSet.valueOf(new long[] { 0x8000000000000001L });
        check(bs.get(0) && bs.get(63), "valueOf should set bits 0 and 63");
        check(bs.previousSetBit(63) == 63, "valueOf: from 63 expected 63");
        check(bs.previousSetBit(62) == 0, "valueOf: from 62 expected 0");
        check(bs.previousSetBit(200) == 63, "valueOf: from 200 expected 63");
    }

    static void testNegativeIndices() {
        BitSet bs = new BitSet();
        bs.set(3);
        check(bs.previousSetBit(-1) == -1, "fromIndex -1 should return -1");
        boolean thrown = false;
        try {
            bs.previousSetBit(-2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "fromIndex -2 should throw IndexOutOfBoundsException");
    }

    static void testAgainstBruteForce() {
        BitSet bs = new BitSet();
        for (int j = 0; j < 300; j++) {
            if (j % 7 == 3) {
                bs.set(j);
            }
        }
        for (int from = 0; from <= 400; from++) {
            int expected = bruteForcePrevious(bs, from);
            int actual = bs.previousSetBit(from);
            check(expected == actual,
                  "mismatch at from=" + from + " expected=" + expected + " actual=" + actual);
        }
    }
}
