/*
 * @test
 * @summary Test for StreamSpliterators.forEachRemaining(IntConsumer)
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.stream.StreamSpliterators;
import java.util.function.IntConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithNullConsumerAndFinished();
    }

    private static void testForEachRemainingWithNullConsumer() {
        StreamSpliterators.IntWrappingSpliterator spliterator = new StreamSpliterators.IntWrappingSpliterator();
        spliterator.finished = false;
        spliterator.buffer = new Object(); // dummy buffer
        try {
            spliterator.forEachRemaining(null);
            // Expect NullPointerException
            System.out.println("Test failed: Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null consumer");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithNullConsumerAndFinished() {
        StreamSpliterators.IntWrappingSpliterator spliterator = new StreamSpliterators.IntWrappingSpliterator();
        spliterator.finished = true;
        spliterator.buffer = null;
        try {
            spliterator.forEachRemaining(null);
            System.out.println("Test failed: Expected NullPointerException for null consumer when finished");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null consumer when finished");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
