/*
 * @test
 * @summary Witup-generated checks for IntPipeline.flatMap through the public IntStream API
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testBasicFlatMap();
        testNullResultTreatedAsEmpty();
        testEmptyInnerStreams();
        testShortCircuitWithLimit();
    }

    static void testNullMapperThrowsNPE() {
        try {
            IntStream.of(1, 2).flatMap(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null mapper");
    }

    static void testBasicFlatMap() {
        int[] actual = IntStream.of(1, 2, 3)
                .flatMap(i -> IntStream.of(i, i * 10))
                .toArray();
        check(Arrays.equals(actual, new int[]{1, 10, 2, 20, 3, 30}),
              "unexpected flatMap result: " + Arrays.toString(actual));
    }

    static void testNullResultTreatedAsEmpty() {
        long count = IntStream.of(1, 2)
                .flatMap(i -> null)
                .count();
        check(count == 0, "null mapper result should contribute no elements, got " + count);
    }

    static void testEmptyInnerStreams() {
        long count = IntStream.of(1, 2, 3)
                .flatMap(i -> IntStream.empty())
                .count();
        check(count == 0, "empty inner streams should produce no elements, got " + count);
    }

    static void testShortCircuitWithLimit() {
        int[] actual = IntStream.range(0, 5)
                .flatMap(i -> IntStream.of(i, i))
                .limit(3)
                .toArray();
        check(Arrays.equals(actual, new int[]{0, 0, 1}),
              "unexpected short-circuit result: " + Arrays.toString(actual));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
