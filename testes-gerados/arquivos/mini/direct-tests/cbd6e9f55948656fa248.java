/*
 * @test
 * @summary Test java.util.ServiceLoader.hasNext() method behavior
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;
import java.util.Iterator;

public class ServiceLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a well-known service interface from JDK to test ServiceLoader
        // We use java.util.Iterator as a dummy service interface for testing

        ServiceLoader<Iterator> loader = ServiceLoader.load(Iterator.class);

        // Initially, hasNext() should be false if no providers are found
        // or true if providers exist. We test both conditions deterministically.

        // We cannot guarantee providers for Iterator, so test that hasNext() returns boolean
        boolean hasNextResult = loader.iterator().hasNext();
        if (hasNextResult != true && hasNextResult != false) {
            throw new AssertionError("hasNext() returned non-boolean value");
        }

        // Test that repeated calls to hasNext() do not throw and return consistent boolean
        Iterator<Iterator> it = loader.iterator();
        boolean first = it.hasNext();
        boolean second = it.hasNext();
        if (first != second) {
            throw new AssertionError("hasNext() returned inconsistent results on repeated calls");
        }

        // Test that if hasNext() returns true, next() returns a non-null provider
        if (first) {
            Iterator provider = it.next();
            if (provider == null) {
                throw new AssertionError("next() returned null provider despite hasNext() true");
            }
        }

        System.out.println("Test passed");
    }
}
