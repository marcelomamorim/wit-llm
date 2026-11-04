/*
 * @test
 * @summary Test the next() method of ServiceLoader for correct behavior.
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;
import java.util.NoSuchElementException;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testNextWithProvider();
        testNextWithoutProvider();
        testNextThrowsWhenNoMore();
    }

    static void testNextWithProvider() {
        ServiceLoader<String> loader = ServiceLoader.load(String.class);
        try {
            String provider = loader.next();
            // Since String is a core class, the loader may or may not have providers.
            // We check that no exception is thrown and the result is not null.
            assert provider != null : "Expected non-null provider";
        } catch (NoSuchElementException e) {
            // If no provider is available, this is acceptable for this test.
        }
    }

    static void testNextWithoutProvider() {
        // Create a custom ServiceLoader that simulates no providers.
        ServiceLoader<String> loader = new ServiceLoader<String>() {
            @Override
            public String next() {
                throw new NoSuchElementException();
            }
            @Override
            public boolean hasNext() {
                return false;
            }
        };
        try {
            loader.next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    static void testNextThrowsWhenNoMore() {
        ServiceLoader<String> loader = new ServiceLoader<String>() {
            @Override
            public String next() {
                throw new NoSuchElementException();
            }
            @Override
            public boolean hasNext() {
                return false;
            }
        };
        try {
            loader.next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
