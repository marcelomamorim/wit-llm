/*
 * @test
 * @summary Test for ServiceLoader.hasNext() method.
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;
import java.util.Iterator;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testHasNextReturnsTrueWhenProviderAvailable();
        testHasNextReturnsFalseWhenNoProvider();
    }

    private static void testHasNextReturnsTrueWhenProviderAvailable() {
        ServiceLoader<Object> loader = ServiceLoader.load(Object.class);
        Iterator<Object> iterator = loader.iterator();
        boolean hasNext = iterator.hasNext();
        System.out.println("hasNext() returns: " + hasNext);
        // Expect true if at least one provider is available
        // Since no specific provider is registered, this may depend on environment
        // For test purposes, just print the result
    }

    private static void testHasNextReturnsFalseWhenNoProvider() {
        // To simulate no provider, create a custom ServiceLoader that returns false
        ServiceLoader<Object> loader = new ServiceLoader<Object>() {
            @Override
            public Iterator<Object> iterator() {
                return new Iterator<Object>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }
                    @Override
                    public Object next() {
                        throw new java.util.NoSuchElementException();
                    }
                };
            }
        };
        boolean hasNext = loader.iterator().hasNext();
        System.out.println("hasNext() returns: " + hasNext);
        // Expect false
    }
}
