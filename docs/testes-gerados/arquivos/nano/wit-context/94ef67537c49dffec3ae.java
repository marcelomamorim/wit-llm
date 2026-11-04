/*
 * @test
 * @summary Tests for java.util.stream.Stream.tryAdvance method.
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.function.Consumer;

public class StreamWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
    }

    private static void testTryAdvanceWithNullAction() {
        Stream<Object> stream = Stream.of(1, 2, 3);
        try {
            stream.tryAdvance(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testTryAdvanceWithValidAction() {
        Stream<Integer> stream = Stream.of(10, 20, 30);
        final int[] sum = {0};
        Consumer<Integer> consumer = (Integer t) -> { sum[0] += t; };
        boolean result = stream.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true");
        }
        if (sum[0] != 10) {
            throw new AssertionError("Consumer did not process the first element correctly");
        }
    }
}
