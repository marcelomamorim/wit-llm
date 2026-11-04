/*
 * @test
 * @summary Test for StreamSpliterators.tryAdvance method, focusing on null input handling.
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.stream.StreamSpliterators;
import java.util.function.Consumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
    }

    public static void testTryAdvanceWithNullConsumer() {
        try {
            StreamSpliterators.tryAdvance(null);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
