/*
 * @test
 * @summary Test for IntStream.tryAdvance method to verify behavior with null and non-null IntConsumer.
 * @run main IntStreamWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntConsumer;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNull();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNull() {
        IntStream stream = IntStream.of(1, 2, 3);
        boolean exceptionThrown = false;
        try {
            stream.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to tryAdvance");
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        IntStream stream = IntStream.of(10, 20, 30);
        final int[] received = {0};
        IntConsumer consumer = value -> received[0] = value;
        boolean result = stream.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true for non-empty stream");
        }
        if (received[0] != 10) {
            throw new AssertionError("Consumer did not receive the expected value. Expected 10, got " + received[0]);
        }
    }
}
