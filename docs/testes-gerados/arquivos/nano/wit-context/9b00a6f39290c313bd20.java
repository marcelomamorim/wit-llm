/*
 * @test
 * @summary Tests the peek method of LongPipeline for null input.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testPeekWithNullAction();
        testPeekWithValidAction();
    }

    private static void testPeekWithNullAction() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        try {
            stream.peek(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for peek(null)");
        }
    }

    private static void testPeekWithValidAction() {
        LongStream stream = LongStream.of(4L, 5L);
        final long[] result = {0};
        LongConsumer consumer = t -> result[0] += t;
        LongStream resultStream = stream.peek(consumer);
        long sum = resultStream.sum();
        if (sum != 9L) {
            System.err.println("Unexpected sum: " + sum);
            System.exit(1);
        } else {
            System.out.println("peek with valid action works as expected");
        }
    }
}
