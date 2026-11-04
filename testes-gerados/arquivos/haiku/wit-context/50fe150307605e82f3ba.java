/*
 * @test
 * @summary Tests LongStream.concat null checks, element order, parallel flag and close handler composition
 * @run main LongStreamWitupTest
 */
import java.util.Arrays;

import java.util.stream.LongStream;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testNullFirstArgumentThrowsNPE();
        testNullSecondArgumentThrowsNPE();
        testBothNullArgumentsThrowsNPE();
        testElementOrderIsPreserved();
        testEmptyStreamsConcatenateToEmpty();
        testParallelFlagPropagates();
        testCloseRunsHandlersOfBothStreams();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFirstArgumentThrowsNPE() {
        try {
            LongStream.concat(null, LongStream.of(1L));
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null first argument");
    }

    static void testNullSecondArgumentThrowsNPE() {
        try {
            LongStream.concat(LongStream.of(1L), null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null second argument");
    }

    static void testBothNullArgumentsThrowsNPE() {
        try {
            LongStream.concat(null, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for both null arguments");
    }

    static void testElementOrderIsPreserved() {
        long[] result = LongStream.concat(LongStream.of(1L, 2L), LongStream.of(3L)).toArray();
        check(Arrays.equals(result, new long[] {1L, 2L, 3L}),
              "concat must keep elements of first stream before second");
    }

    static void testEmptyStreamsConcatenateToEmpty() {
        long count = LongStream.concat(LongStream.empty(), LongStream.empty()).count();
        check(count == 0L, "concat of empty streams must be empty");
    }

    static void testParallelFlagPropagates() {
        LongStream result = LongStream.concat(LongStream.of(1L).parallel(), LongStream.of(2L));
        check(result.isParallel(), "concat must be parallel when either input is parallel");
    }

    static void testCloseRunsHandlersOfBothStreams() {
        final int[] closed = {0};
        LongStream a = LongStream.of(1L).onClose(() -> closed[0] += 1);
        LongStream b = LongStream.of(2L).onClose(() -> closed[0] += 10);
        LongStream result = LongStream.concat(a, b);
        result.close();
        check(closed[0] == 11, "closing concat result must close both inputs");
    }
}
