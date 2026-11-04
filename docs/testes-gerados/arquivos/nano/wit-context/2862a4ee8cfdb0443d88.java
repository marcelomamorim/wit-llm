/*
 * @test
 * @summary Test for StreamSpliterators.tryAdvance(DoubleConsumer)
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.stream.StreamSpliterators;
import java.util.function.DoubleConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
    }

    private static void testTryAdvanceWithNullAction() {
        boolean threwException = false;
        try {
            DoubleConsumer nullConsumer = null;
            StreamSpliterators.tryAdvance(nullConsumer);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceWithValidAction() {
        final boolean[] called = {false};
        DoubleConsumer consumer = d -> { called[0] = true; };
        boolean result = StreamSpliterators.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true");
        }
        if (!called[0]) {
            throw new AssertionError("Consumer was not called");
        }
    }
}
