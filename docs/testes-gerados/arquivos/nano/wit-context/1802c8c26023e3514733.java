/*
 * @test
 * @summary Test for StreamSpliterators.tryAdvance(IntConsumer)
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.stream.StreamSpliterators;
import java.util.function.IntConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
    }

    private static void testTryAdvanceWithNullAction() {
        boolean threwNullPointer = false;
        try {
            StreamSpliterators.tryAdvance(null);
        } catch (NullPointerException e) {
            threwNullPointer = true;
        }
        if (!threwNullPointer) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceWithValidAction() {
        final int[] value = {0};
        IntConsumer consumer = v -> value[0] = v;
        boolean result = StreamSpliterators.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true for valid action");
        }
        if (value[0] != 0) {
            throw new AssertionError("Consumer should have been called with value 0");
        }
    }
}
