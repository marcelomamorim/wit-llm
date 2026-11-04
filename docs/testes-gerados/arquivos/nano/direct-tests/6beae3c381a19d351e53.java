/*
 * @test
 * @summary Test tryAdvance method of StreamSpliterators for basic behavior
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.function.Consumer;
import java.util.stream.StreamSpliterators;
import java.util.NoSuchElementException;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNonNullConsumer();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithNonNullConsumer() {
        // Create a dummy Consumer that records the value
        class DummyConsumer implements Consumer<Integer> {
            Integer received = null;
            public void accept(Integer t) {
                received = t;
            }
        }
        DummyConsumer consumer = new DummyConsumer();
        // Call tryAdvance with a non-null consumer
        boolean result = StreamSpliterators.tryAdvance(consumer);
        // Verify that the consumer received a value and method returned true
        if (result != true) {
            throw new AssertionError("Expected true from tryAdvance") ;
        }
        if (consumer.received == null) {
            throw new AssertionError("Consumer did not receive any value") ;
        }
        System.out.println("testTryAdvanceWithNonNullConsumer passed");
    }

    static void testTryAdvanceWithNullConsumer() {
        boolean exceptionThrown = false;
        try {
            StreamSpliterators.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null consumer") ;
        }
        System.out.println("testTryAdvanceWithNullConsumer passed");
    }
}
