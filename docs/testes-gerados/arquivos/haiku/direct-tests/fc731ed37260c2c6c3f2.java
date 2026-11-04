/*
 * @test
 * @summary Witup-style tests for BitSet(int nbits) constructor
 * @run main BitSetWitupTest
 */

public class BitSetWitupTest {
    public static void main(String[] args) {
        BitSet empty = new BitSet(0);
        check(empty.isEmpty(), "new BitSet(0) must be empty");
        check(empty.length() == 0, "length of BitSet(0) must be 0");
        check(empty.cardinality() == 0, "cardinality of BitSet(0) must be 0");
        empty.set(5);
        check(empty.get(5), "BitSet(0) must grow on set(5)");
        check(empty.length() == 6, "length after set(5) must be 6");

        BitSet b = new BitSet(100);
        check(b.isEmpty(), "new BitSet(100) must be empty");
        check(b.size() >= 100, "size of BitSet(100) must be at least 100");
        check(b.cardinality() == 0, "cardinality of new BitSet(100) must be 0");
        b.set(99);
        check(b.get(99), "bit 99 must be set");
        check(b.cardinality() == 1, "cardinality must be 1");
        check(b.length() == 100, "length must be 100");

        int[] bad = {-1, Integer.MIN_VALUE};
        for (int n : bad) {
            try {
                new BitSet(n);
                check(false, "new BitSet(" + n + ") must throw NegativeArraySizeException");
            } catch (NegativeArraySizeException expected) {
                // expected
            }
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
