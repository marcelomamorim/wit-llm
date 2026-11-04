/* @test    @summary Test for BitSet.forEachRemaining to ensure it processes bits correctly and handles edge cases.    @run main BitSetWitupTest */

import java.util.BitSet;
import java.util.function.IntConsumer;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithSetBits();
        testForEachRemainingWithEmptyBitSet();
        testForEachRemainingWithMaxValueBit();
    }

    static void testForEachRemainingWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(10);
        bs.set(64);
        final StringBuilder result = new StringBuilder();
        bs.forEachRemaining(new IntConsumer() {
            @Override
            public void accept(int value) {
                result.append(value).append(",");
            }
        });
        String expected = "0,10,64,";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + " but got: " + result);
        }
    }

    static void testForEachRemainingWithEmptyBitSet() {
        BitSet bs = new BitSet();
        final StringBuilder result = new StringBuilder();
        bs.forEachRemaining(new IntConsumer() {
            @Override
            public void accept(int value) {
                result.append(value).append(",");
            }
        });
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result but got: " + result);
        }
    }

    static void testForEachRemainingWithMaxValueBit() {
        BitSet bs = new BitSet();
        bs.set(Integer.MAX_VALUE);
        final StringBuilder result = new StringBuilder();
        bs.forEachRemaining(new IntConsumer() {
            @Override
            public void accept(int value) {
                result.append(value).append(",");
            }
        });
        String expected = Integer.MAX_VALUE + ",";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + " but got: " + result);
        }
    }
}
