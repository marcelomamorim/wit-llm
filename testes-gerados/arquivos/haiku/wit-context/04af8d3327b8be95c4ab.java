/*
 * @test
 * @summary Verifies that ServiceLoader.next() behavior is observable through public iterator and stream APIs when a service has no providers
 * @run main ServiceLoaderWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface NoProvidersService {
    }

    public static void main(String[] args) {
        testStreamOfProvidersIsEmpty();
        testIteratorExhaustionThrowsNoSuchElementException();
        testRepeatedNextAfterExhaustionThrows();
    }

    static void testStreamOfProvidersIsEmpty() {
        ServiceLoader<NoProvidersService> loader = ServiceLoader.load(NoProvidersService.class);
        long count = loader.stream().count();
        if (count != 0) {
            throw new RuntimeException("Expected no providers, got " + count);
        }
    }

    static void testIteratorExhaustionThrowsNoSuchElementException() {
        ServiceLoader<NoProvidersService> loader = ServiceLoader.load(NoProvidersService.class);
        Iterator<NoProvidersService> it = loader.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("Expected hasNext() to be false");
        }
        try {
            it.next();
            throw new RuntimeException("Expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testRepeatedNextAfterExhaustionThrows() {
        ServiceLoader<NoProvidersService> loader = ServiceLoader.load(NoProvidersService.class);
        Iterator<NoProvidersService> it = loader.iterator();
        for (int i = 0; i < 2; i++) {
            try {
                it.next();
                throw new RuntimeException("Expected NoSuchElementException on attempt " + i);
            } catch (NoSuchElementException expected) {
                // expected
            }
        }
    }
}
