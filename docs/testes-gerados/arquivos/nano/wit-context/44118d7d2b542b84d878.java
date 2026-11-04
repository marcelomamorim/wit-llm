/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(DoubleConsumer) method.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        Spliterators.OfDouble spliterator = new Spliterators.OfDouble() {
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                return false;
            }
        };
        try {
            spliterator.forEachRemaining(null);
            // If no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null action.");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidAction() {
        final boolean[] called = {false};
        DoubleConsumer consumer = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                called[0] = true;
            }
        };
        Spliterators.OfDouble spliterator = new Spliterators.OfDouble() {
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                if (action != null) {
                    action.accept(1.23);
                    return true;
                }
                return false;
            }
            @Override
            public void forEachRemaining(DoubleConsumer action) {
                while (tryAdvance(action)) {}
            }
        };
        spliterator.forEachRemaining(consumer);
        if (called[0]) {
            System.out.println("Passed: forEachRemaining invoked consumer successfully.");
        } else {
            System.err.println("Failed: Consumer was not invoked.");
        }
    }
}
