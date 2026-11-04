/*
 * @test
 * @summary Test tryAdvance method of StreamSpliterators for LongConsumer
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.stream.StreamSpliterators;
import java.util.function.LongConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithValidConsumer() {
        long[] valueHolder = {42L};
        LongConsumer consumer = v -> {
            valueHolder[0] = v;
        };
        boolean result = false;
        try {
            result = StreamSpliterators.tryAdvance(consumer);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
        assert result : "tryAdvance should return true when called with valid consumer";
        assert valueHolder[0] == 42L : "Consumer should have accepted the value 42";
    }

    static void testTryAdvanceWithNullConsumer() {
        try {
            StreamSpliterators.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
