/*
 * @test
 * @summary Verify LongStream.peek null-action NPE and observable lazy, ordered, pass-through behavior via public API
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testPeekObservesElementsInOrder();
        testPeekIsLazyUntilTerminalOperation();
        testPeekPassesElementsThroughUnchanged();
    }

    static void testNullActionThrowsNPE() {
        boolean thrown = false;
        try {
            LongStream.of(1L, 2L).peek(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testPeekObservesElementsInOrder() {
        List<Long> seen = new ArrayList<>();
        long sum = LongStream.of(3L, 1L, 2L)
                .peek(v -> { seen.add(v); })
                .sum();
        if (sum != 6L) {
            throw new RuntimeException("Unexpected sum: " + sum);
        }
        List<Long> expected = new ArrayList<>();
        expected.add(3L);
        expected.add(1L);
        expected.add(2L);
        if (!expected.equals(seen)) {
            throw new RuntimeException("Unexpected peek order: " + seen);
        }
    }

    static void testPeekIsLazyUntilTerminalOperation() {
        List<Long> seen = new ArrayList<>();
        LongStream stream = LongStream.of(10L, 20L).peek(v -> { seen.add(v); });
        if (!seen.isEmpty()) {
            throw new RuntimeException("peek action ran before terminal operation: " + seen);
        }
        long count = stream.count();
        if (count != 2L || seen.size() != 2) {
            throw new RuntimeException("Unexpected count or peek invocations: " + count + " " + seen);
        }
    }

    static void testPeekPassesElementsThroughUnchanged() {
        long[] result = LongStream.of(5L, -7L, 0L, Long.MAX_VALUE)
                .peek(v -> { })
                .toArray();
        long[] expected = {5L, -7L, 0L, Long.MAX_VALUE};
        if (result.length != expected.length) {
            throw new RuntimeException("Unexpected length: " + result.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (result[i] != expected[i]) {
                throw new RuntimeException("Mismatch at " + i + ": " + result[i]);
            }
        }
    }
}
