/*
 * @test
 * @summary Test for StreamSpliterators.tryAdvance(LongConsumer)
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.stream.StreamSpliterators;
import java.util.function.LongConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
    }

    private static void testTryAdvanceWithNullAction() {
        boolean threwException = false;
        try {
            LongConsumer nullConsumer = null;
            boolean result = StreamSpliterators.tryAdvance(nullConsumer);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceWithValidAction() {
        final long[] captured = {0};
        LongConsumer consumer = (long value) -> {
            captured[0] = value;
        };
        boolean result = StreamSpliterators.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true for valid action");
        }
        if (captured[0] != 0L) {
            throw new AssertionError("Consumer did not accept the expected value");
        }
    }
}
