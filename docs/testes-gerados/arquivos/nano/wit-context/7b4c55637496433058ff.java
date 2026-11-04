/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(IntConsumer) method.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        Spliterators.OfInt spliterator = new Spliterators.OfInt();
        try {
            spliterator.forEachRemaining(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testForEachRemainingWithValidAction() {
        Spliterators.OfInt spliterator = new Spliterators.OfInt();
        final boolean[] called = {false};
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                called[0] = true;
            }
        };
        try {
            spliterator.forEachRemaining(action);
            // No exception expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
        if (!called[0]) {
            System.err.println("Failed: Action was not called");
            System.exit(1);
        }
    }
}
