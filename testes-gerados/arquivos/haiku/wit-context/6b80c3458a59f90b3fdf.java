/*
 * @test
 * @summary Witup-generated tests for BitSet.flip(int)
 * @run main BitSetWitupTest
 */

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeIndexThrows();
        testFlipSetsAndClearsBit();
        testFlipExpandsBeyondCurrentSize();
        testFlipTwiceRestoresEmptySet();
        testFlipDoesNotAffectOtherBits();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeIndexThrows() {
        BitSet bs = new BitSet();
        try {
            bs.flip(-1);
            throw new RuntimeException("expected IndexOutOfBoundsException for flip(-1)");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        check(bs.isEmpty(), "set modified after failed flip(-1)");

        BitSet nonEmpty = new BitSet();
        nonEmpty.set(5);
        try {
            nonEmpty.flip(-5);
            throw new RuntimeException("expected IndexOutOfBoundsException for flip(-5)");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        check(nonEmpty.get(5), "bit 5 changed after failed flip(-5)");
        check(nonEmpty.cardinality() == 1, "cardinality changed after failed flip(-5)");
    }

    static void testFlipSetsAndClearsBit() {
        BitSet bs = new BitSet();
        bs.flip(3);
        check(bs.get(3), "bit 3 not set after flip");
        check(bs.cardinality() == 1, "cardinality not 1 after first flip");
        bs.flip(3);
        check(!bs.get(3), "bit 3 still set after second flip");
        check(bs.cardinality() == 0, "cardinality not 0 after second flip");
    }

    static void testFlipExpandsBeyondCurrentSize() {
        BitSet bs = new BitSet();
        bs.flip(70);
        check(bs.get(70), "bit 70 not set after flip beyond initial size");
        check(bs.length() == 71, "length not 71 after flip(70), was " + bs.length());
        check(bs.cardinality() == 1, "cardinality not 1 after flip(70)");
    }

    static void testFlipTwiceRestoresEmptySet() {
        BitSet bs = new BitSet();
        bs.flip(70);
        bs.flip(70);
        check(bs.isEmpty(), "set not empty after flipping same bit twice");
        check(bs.length() == 0, "length not 0 after restoring empty set, was " + bs.length());
    }

    static void testFlipDoesNotAffectOtherBits() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(64);
        bs.flip(2);
        check(bs.get(1), "bit 1 changed by flip(2)");
        check(bs.get(2), "bit 2 not set by flip(2)");
        check(bs.get(64), "bit 64 changed by flip(2)");
        check(bs.cardinality() == 3, "cardinality not 3 after flip(2), was " + bs.cardinality());
        check(bs.length() == 65, "length not 65 after flip(2), was " + bs.length());
    }
}
