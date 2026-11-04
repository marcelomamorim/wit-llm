/*
 * @test
 * @summary Test for java.util.Spliterators.tryAdvance(C consumer) method.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.Objects;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        try {
            boolean result = Spliterators.tryAdvance(null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null consumer.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("Passed: NullPointerException thrown as expected for null consumer.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        java.util.List<Integer> list = java.util.Arrays.asList(1, 2, 3);
        java.util.Iterator<Integer> iterator = list.iterator();
        boolean result = false;
        try {
            result = Spliterators.tryAdvance(consumer -> {
                if (iterator.hasNext()) {
                    int val = iterator.next();
                    System.out.println("Consumed: " + val);
                }
            });
            // Since the method always returns false, check for that
            if (result != false) {
                System.err.println("Failed: Expected false from tryAdvance.");
                System.exit(1);
            } else {
                System.out.println("Passed: tryAdvance returned false as expected.");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
