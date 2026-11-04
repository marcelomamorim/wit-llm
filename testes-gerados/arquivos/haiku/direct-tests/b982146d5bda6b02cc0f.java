/*
 * @test
 * @summary Verifies IntPipeline.flatMap flattening, null-result handling, null-mapper rejection, inner stream closing and short-circuiting
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkArray(int[] actual, int[] expected, String message) {
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException(message + ": expected " + Arrays.toString(expected)
                    + " but was " + Arrays.toString(actual));
        }
    }

    static void testFlattenOrder() {
        int[] result = IntStream.of(1, 2, 3)
                .flatMap(i -> IntStream.of(i, i * 10))
                .toArray();
        checkArray(result, new int[] {1, 10, 2, 20, 3, 30}, "flatMap order");
    }

    static void testEmptyInnerStreams() {
        int[] result = IntStream.of(1, 2, 3)
                .flatMap(i -> IntStream.empty())
                .toArray();
        checkArray(result, new int[0], "flatMap with empty inner streams");
    }

    static void testNullResultIsSkipped() {
        int[] result = IntStream.of(1, 2, 3)
                .flatMap(i -> i == 2 ? null : IntStream.of(i))
                .toArray();
        checkArray(result, new int[] {1, 3}, "flatMap null inner stream skipped");
    }

    static void testNullMapperRejected() {
        boolean thrown = false;
        try {
            IntStream.of(1).flatMap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "flatMap(null) must throw NullPointerException");
    }

    static void testInnerStreamClosed() {
        boolean[] closed = new boolean[1];
        int[] result = IntStream.of(7)
                .flatMap(i -> IntStream.of(i, i + 1).onClose(() -> closed[0] = true))
                .toArray();
        checkArray(result, new int[] {7, 8}, "flatMap elements with onClose");
        check(closed[0], "inner stream must be closed after flatMap consumes it");
    }

    static void testShortCircuitWithLimit() {
        int[] result = IntStream.of(1, 2)
                .flatMap(i -> IntStream.range(0, 1000000))
                .limit(3)
                .toArray();
        checkArray(result, new int[] {0, 1, 2}, "flatMap with limit short-circuit");
    }

    public static void main(String[] args) {
        testFlattenOrder();
        testEmptyInnerStreams();
        testNullResultIsSkipped();
        testNullMapperRejected();
        testInnerStreamClosed();
        testShortCircuitWithLimit();
    }
}
