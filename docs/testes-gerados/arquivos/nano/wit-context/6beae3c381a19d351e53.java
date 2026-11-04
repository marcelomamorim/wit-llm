/*
 * @test
 * @summary Test for StreamSpliterators.tryAdvance method handling null Consumer
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.stream.StreamSpliterators;
import java.util.function.Consumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        boolean threwException = false;
        try {
            StreamSpliterators.tryAdvance(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        final boolean[] called = {false};
        Consumer<Object> consumer = obj -> { called[0] = true; };
        boolean result = StreamSpliterators.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when called with valid Consumer");
        }
        if (!called[0]) {
            throw new AssertionError("Consumer was not called during tryAdvance");
        }
    }
}
