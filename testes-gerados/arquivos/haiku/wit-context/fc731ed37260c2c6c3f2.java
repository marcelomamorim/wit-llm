/*
 * @test
 * @summary Verifies BitSet(int nbits) rejects negative sizes and accepts zero and positive sizes
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows();
        testMinIntegerThrows();
        testZeroSizeAccepted();
        testPositiveSizeAccepted();
    }

    static void testNegativeSizeThrows() {
        try {
            new BitSet(-1);
            throw new RuntimeException("Expected NegativeArraySizeException for nbits = -1");
        } catch (NegativeArraySizeException expected) {
            // expected
        }
    }

    static void testMinIntegerThrows() {
        try {
            new BitSet(Integer.MIN_VALUE);
            throw new RuntimeException("Expected NegativeArraySizeException for Integer.MIN_VALUE");
        } catch (NegativeArraySizeException expected) {
            // expected
        }
    }

    static void testZeroSizeAccepted() {
        BitSet bs = new BitSet(0);
        if (!bs.isEmpty()) {
            throw new RuntimeException("New BitSet(0) should be empty");
        }
        if (bs.length() != 0) {
            throw new RuntimeException("length() should be 0, was " + bs.length());
        }
        bs.set(5);
        if (!bs.get(5)) {
            throw new RuntimeException("bit 5 should be set");
        }
        if (bs.cardinality() != 1) {
            throw new RuntimeException("cardinality should be 1, was " + bs.cardinality());
        }
    }

    static void testPositiveSizeAccepted() {
        BitSet bs = new BitSet(100);
        if (!bs.isEmpty()) {
            throw new RuntimeException("New BitSet(100) should be empty");
        }
        if (bs.cardinality() != 0) {
            throw new RuntimeException("cardinality should be 0");
        }
        bs.set(99);
        if (!bs.get(99)) {
            throw new RuntimeException("bit 99 should be set");
        }
        if (bs.length() != 100) {
            throw new RuntimeException("length should be 100, was " + bs.length());
        }
    }
}
