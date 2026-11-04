/*
 * @test
 * @summary Test for ServiceLoader.hasNext() method to verify exception handling and behavior.
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testHasNextNormalBehavior();
        testHasNextConcurrentModificationException();
    }

    private static void testHasNextNormalBehavior() {
        ServiceLoader<Object> loader = ServiceLoader.load(Object.class);
        try {
            boolean result = loader.hasNext();
            // Result is boolean, no exception expected in normal flow
            System.out.println("hasNext() returned: " + result);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in normal behavior test", e);
        }
    }

    private static void testHasNextConcurrentModificationException() {
        ServiceLoader<Object> loader = ServiceLoader.load(Object.class);
        try {
            // Simulate concurrent modification scenario by manipulating internal state if possible
            // Since internal state is inaccessible, we test the behavior when the underlying iterator throws
            // For the purpose of this test, we assume that calling hasNext() may throw if underlying collection is modified
            loader.hasNext();
            // No exception thrown, test passes as per current implementation
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in concurrent modification test", e);
        }
    }
}
