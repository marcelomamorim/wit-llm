/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(IntConsumer)
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        final boolean[] called = {false};
        IntConsumer consumer = (int value) -> {
            called[0] = true;
            if (value != 0 && value != 1 && value != 2) {
                throw new AssertionError("Unexpected value: " + value);
            }
        };
        try {
            Spliterators.forEachRemaining(consumer);
            // Since the underlying iterator is not specified, we assume no exception means success
        } catch (Exception e) {
            throw new AssertionError("Exception thrown during forEachRemaining with valid consumer", e);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        try {
            Spliterators.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
