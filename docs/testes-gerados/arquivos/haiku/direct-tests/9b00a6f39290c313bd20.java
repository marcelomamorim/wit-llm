/*
 * @test
 * @summary Behavioral checks for LongStream.peek (LongPipeline.peek): ordering, laziness, null action and value pass-through
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testPeekSeesElementsInOrder();
        testPeekIsLazy();
        testPeekNullActionThrowsNpe();
        testPeekPreservesValues();
    }

    static void testPeekSeesElementsInOrder() {
        List<Long> seen = new ArrayList<>();
        long sum = LongStream.of(1L, 2L, 3L)
                .peek(t -> seen.add(t))
                .sum();
        if (sum != 6L) {
            throw new RuntimeException("unexpected sum: " + sum);
        }
        if (seen.size() != 3
                || seen.get(0).longValue() != 1L
                || seen.get(1).longValue() != 2L
                || seen.get(2).longValue() != 3L) {
            throw new RuntimeException("peek did not observe elements in order: " + seen);
        }
    }

    static void testPeekIsLazy() {
        int[] calls = {0};
        LongStream s = LongStream.of(1L, 2L).peek(t -> calls[0]++);
        if (calls[0] != 0) {
            throw new RuntimeException("peek action invoked before terminal operation");
        }
        s.sum();
        if (calls[0] != 2) {
            throw new RuntimeException("expected 2 invocations, got " + calls[0]);
        }
    }

    static void testPeekNullActionThrowsNpe() {
        try {
            LongStream.of(1L).peek(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("peek(null) did not throw NullPointerException");
    }

    static void testPeekPreservesValues() {
        long[] result = LongStream.of(5L, 6L).peek(t -> { }).toArray();
        if (!Arrays.equals(result, new long[] {5L, 6L})) {
            throw new RuntimeException("values changed by peek: " + Arrays.toString(result));
        }
    }
}
