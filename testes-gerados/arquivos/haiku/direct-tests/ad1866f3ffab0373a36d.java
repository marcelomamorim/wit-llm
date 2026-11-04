/*
 * @test
 * @summary Behavioral checks for ReferencePipeline.flatMapToInt: null mapper, null inner streams, empty inner streams, ordering, and cancellation.
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNpe();
        testFlatteningSum();
        testNullInnerStreamIsSkipped();
        testEmptyInnerStreams();
        testOrderingPreserved();
        testShortCircuitWithLimit();
        testAnyMatchShortCircuit();
    }

    static void testNullMapperThrowsNpe() {
        try {
            Stream.of("a").flatMapToInt(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null mapper");
    }

    static void testFlatteningSum() {
        int sum = Stream.of("ab", "c").flatMapToInt(s -> s.chars()).sum();
        // 'a' + 'b' + 'c' = 97 + 98 + 99
        check(sum == 294, "flatten sum expected 294 but was " + sum);
    }

    static void testNullInnerStreamIsSkipped() {
        long count = Stream.of(1, 2, 3)
                .flatMapToInt(i -> i == 2 ? null : IntStream.of(i, i))
                .count();
        check(count == 4, "expected 4 elements but was " + count);
    }

    static void testEmptyInnerStreams() {
        long count = Stream.of(1, 2)
                .flatMapToInt(i -> IntStream.empty())
                .count();
        check(count == 0, "expected 0 elements but was " + count);
    }

    static void testOrderingPreserved() {
        int[] actual = Stream.of(1, 2)
                .flatMapToInt(i -> IntStream.of(i * 10, i * 10 + 1))
                .toArray();
        int[] expected = {10, 11, 20, 21};
        check(Arrays.equals(actual, expected),
              "ordering mismatch: " + Arrays.toString(actual));
    }

    static void testShortCircuitWithLimit() {
        // Infinite upstream: must terminate via cancellation path.
        // Elements: 1, -1, 2 -> sum 2
        int sum = Stream.iterate(1, i -> i + 1)
                .flatMapToInt(i -> IntStream.of(i, -i))
                .limit(3)
                .sum();
        check(sum == 2, "limit short-circuit expected 2 but was " + sum);
    }

    static void testAnyMatchShortCircuit() {
        boolean found = Stream.of(1, 2, 3)
                .flatMapToInt(i -> IntStream.of(i))
                .anyMatch(x -> x == 2);
        check(found, "anyMatch expected true");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
