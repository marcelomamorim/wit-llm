/*
 * @test
 * @summary Test the next() method of ServiceLoader for correct behavior.
 * @run main ServiceLoaderWitupTest
 */

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testNextWithProvider();
        testNextWithoutProvider();
        testNextThrowsWhenNoMoreProviders();
    }

    static void testNextWithProvider() {
        ServiceLoader<String> loader = ServiceLoader.load(String.class);
        // Simulate a provider being available
        // Since we cannot modify the internal state, we test the behavior assuming a provider exists
        // For the purpose of this test, we check that calling next() returns a non-null value or throws if none
        try {
            String provider = loader.iterator().hasNext() ? loader.iterator().next() : null;
            if (provider != null) {
                String result = loader.next();
                assert result != null : "Expected non-null provider";
            }
        } catch (NoSuchElementException e) {
            // Expected if no provider is available
        }
    }

    static void testNextWithoutProvider() {
        ServiceLoader<Object> loader = ServiceLoader.load(Object.class);
        // Force no providers by using a custom class loader or mock if possible
        // Since we cannot modify internal state, we simulate by calling iterator().hasNext()
        if (!loader.iterator().hasNext()) {
            try {
                loader.next();
                assert false : "Expected NoSuchElementException";
            } catch (NoSuchElementException e) {
                // Expected exception
            }
        }
    }

    static void testNextThrowsWhenNoMoreProviders() {
        ServiceLoader<Integer> loader = ServiceLoader.load(Integer.class);
        // Consume all providers
        while (loader.iterator().hasNext()) {
            loader.iterator().next();
        }
        // Now calling next() should throw
        try {
            loader.next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
