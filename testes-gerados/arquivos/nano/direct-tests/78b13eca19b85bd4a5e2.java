/* @test    @summary Test for Streams.forEachRemaining method to ensure all elements are processed correctly.    @run main StreamsWitupTest */

import java.util.stream.Streams;
import java.util.function.IntConsumer;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingProcessesAllElements();
    }

    public static void testForEachRemainingProcessesAllElements() {
        final StringBuilder result = new StringBuilder();
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                result.append(value).append(",");
            }
        };
        // Create a Streams object with from=0, upTo=5, last=1 (closed range with one last element)
        Streams streams = new Streams(0, 5, 1);
        // Call forEachRemaining, expecting to process 0,1,2,3,4,5
        streams.forEachRemaining(consumer);
        // Verify that all elements from 0 to 5 are processed
        String expected = "0,1,2,3,4,5,";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + " but got: " + result.toString());
        }
    }
}
