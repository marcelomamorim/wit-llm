/*
 * @test
 * @summary Witup generated checks for SplittableRandom double spliterator forEachRemaining
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testEmptySpliteratorNullConsumerThrowsNPE();
        testForEachRemainingMatchesToArray();
        testExhaustedAfterForEachRemaining();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfDouble s = new SplittableRandom(42L).doubles(3).spliterator();
        DoubleConsumer nullConsumer = null;
        try {
            s.forEachRemaining(nullConsumer);
            check(false, "expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptySpliteratorNullConsumerThrowsNPE() {
        Spliterator.OfDouble s = new SplittableRandom(1L).doubles(0).spliterator();
        DoubleConsumer nullConsumer = null;
        try {
            s.forEachRemaining(nullConsumer);
            check(false, "expected NullPointerException for null consumer on empty spliterator");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachRemainingMatchesToArray() {
        double[] expected = new SplittableRandom(7L).doubles(10).toArray();
        Spliterator.OfDouble s = new SplittableRandom(7L).doubles(10).spliterator();
        final double[] got = new double[10];
        final int[] idx = {0};
        s.forEachRemaining((DoubleConsumer) d -> got[idx[0]++] = d);
        check(idx[0] == 10, "expected 10 values, got " + idx[0]);
        check(Arrays.equals(expected, got), "forEachRemaining values differ from toArray values");
    }

    static void testExhaustedAfterForEachRemaining() {
        Spliterator.OfDouble s = new SplittableRandom(99L).doubles(4).spliterator();
        final int[] first = {0};
        s.forEachRemaining((DoubleConsumer) d -> first[0]++);
        check(first[0] == 4, "expected 4 values on first traversal, got " + first[0]);
        final int[] second = {0};
        s.forEachRemaining((DoubleConsumer) d -> second[0]++);
        check(second[0] == 0, "expected no values after exhaustion, got " + second[0]);
    }
}
