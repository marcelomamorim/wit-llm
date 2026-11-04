/*
 * @test
 * @summary Test for java.util.Spliterators.forEachRemaining(C consumer) method.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.Objects;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    public static void testForEachRemainingWithNullConsumer() {
        try {
            Spliterators.forEachRemaining(null);
            // If no exception, test fails
            System.err.println("FAIL: Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("PASS: NullPointerException thrown as expected for null consumer");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    public static void testForEachRemainingWithValidConsumer() {
        try {
            // Use a simple consumer that does nothing
            java.util.function.Consumer<Object> consumer = obj -> {};
            // Call with a non-null spliterator (empty)
            Spliterators.emptySpliterator().forEachRemaining(consumer);
            System.out.println("PASS: forEachRemaining with valid consumer executed without exception");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
