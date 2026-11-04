/*
 * @test
 * @summary Test java.util.BitSet forEachRemaining(IntConsumer) method
 * @run main BitSetWitupTest
 */

import java.util.BitSet;
import java.util.function.IntConsumer;
import java.util.Objects;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testNullAction();
        testEmptyBitSet();
        testSingleBitSet();
        testMultipleBitsSet();
        testMaxIntBitSet();
    }

    private static void testNullAction() {
        BitSet bs = new BitSet();
        try {
            bs.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyBitSet() {
        BitSet bs = new BitSet();
        bs.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                throw new RuntimeException("No bits should be set, but got: " + value);
            }
        });
    }

    private static void testSingleBitSet() {
        final int bitIndex = 5;
        BitSet bs = new BitSet();
        bs.set(bitIndex);
        final boolean[] called = {false};
        bs.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                if (value != bitIndex) {
                    throw new RuntimeException("Expected bit " + bitIndex + " but got " + value);
                }
                called[0] = true;
            }
        });
        if (!called[0]) {
            throw new RuntimeException("Consumer was not called for single set bit");
        }
    }

    private static void testMultipleBitsSet() {
        final int[] expected = {1, 3, 64, 127};
        BitSet bs = new BitSet();
        for (int bit : expected) {
            bs.set(bit);
        }
        final int[] index = {0};
        bs.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                if (index[0] >= expected.length) {
                    throw new RuntimeException("More bits than expected");
                }
                if (value != expected[index[0]]) {
                    throw new RuntimeException("Expected bit " + expected[index[0]] + " but got " + value);
                }
                index[0]++;
            }
        });
        if (index[0] != expected.length) {
            throw new RuntimeException("Fewer bits than expected");
        }
    }

    private static void testMaxIntBitSet() {
        BitSet bs = new BitSet();
        bs.set(Integer.MAX_VALUE);
        final boolean[] called = {false};
        bs.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                if (value != Integer.MAX_VALUE) {
                    throw new RuntimeException("Expected Integer.MAX_VALUE bit but got " + value);
                }
                called[0] = true;
            }
        });
        if (!called[0]) {
            throw new RuntimeException("Consumer was not called for Integer.MAX_VALUE bit");
        }
    }
}
