/*
 * @test
 * @summary Test tryAdvance method of StreamSpliterators for IntConsumer
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.stream.StreamSpliterators;
import java.util.function.IntConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidInput();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithValidInput() {
        // Create a dummy IntSupplier that returns a fixed value
        java.util.function.IntSupplier supplier = () -> 42;
        // Wrap the supplier into a spliterator
        java.util.Spliterator.OfInt spliterator = new java.util.Spliterators.AbstractIntSpliterator(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(IntConsumer action) {
                return StreamSpliterators.tryAdvance(this, action);
            }
        };
        // Create an IntConsumer to verify the value
        final int[] received = new int[1];
        IntConsumer consumer = (value) -> received[0] = value;
        // Call tryAdvance
        boolean result = spliterator.tryAdvance(consumer);
        // Verify that the consumer received the value 42
        if (result && received[0] == 42) {
            System.out.println("testTryAdvanceWithValidInput passed");
        } else {
            throw new AssertionError("testTryAdvanceWithValidInput failed");
        }
    }

    static void testTryAdvanceWithNullConsumer() {
        try {
            // Call tryAdvance with null, expecting NullPointerException
            StreamSpliterators.tryAdvance(null, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceWithNullConsumer passed");
        }
    }
}
