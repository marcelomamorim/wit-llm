/*
 * @test
 * @summary Tests LongStream.iterate(seed, f) sequence, laziness, overflow wrap-around and null function handling
 * @run main LongStreamWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testSequenceFromSeed();
        testFunctionAppliedLazily();
        testOverflowWrapsAround();
        testNullFunctionThrowsNPE();
        System.out.println("PASSED");
    }

    static void testSequenceFromSeed() {
        long[] actual = LongStream.iterate(1L, x -> x * 2).limit(5).toArray();
        long[] expected = {1L, 2L, 4L, 8L, 16L};
        check(Arrays.equals(expected, actual), "sequence mismatch: " + Arrays.toString(actual));

        long first = LongStream.iterate(42L, x -> x + 1).findFirst().getAsLong();
        check(first == 42L, "first element must be seed, was " + first);
    }

    static void testFunctionAppliedLazily() {
        long[] calls = {0};
        LongStream s = LongStream.iterate(0L, x -> {
            calls[0]++;
            return x + 1;
        });
        check(calls[0] == 0, "function must not be invoked on creation");
        long[] actual = s.limit(3).toArray();
        check(Arrays.equals(new long[]{0L, 1L, 2L}, actual), "unexpected elements " + Arrays.toString(actual));
        check(calls[0] == 2, "function should be applied exactly 2 times, was " + calls[0]);
    }

    static void testOverflowWrapsAround() {
        long[] actual = LongStream.iterate(Long.MAX_VALUE - 1, x -> x + 1).limit(3).toArray();
        long[] expected = {Long.MAX_VALUE - 1, Long.MAX_VALUE, Long.MIN_VALUE};
        check(Arrays.equals(expected, actual), "overflow mismatch: " + Arrays.toString(actual));
    }

    static void testNullFunctionThrowsNPE() {
        boolean thrown = false;
        try {
            LongStream.iterate(0L, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null function must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
