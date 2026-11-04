/*
 * @test
 * @summary Deterministic tests for java.util.ServiceLoader.hasNext()
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class ServiceLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextBasic();
        testHasNextConcurrentModification();
    }

    // Test basic hasNext behavior with a known service and no concurrent modification
    private static void testHasNextBasic() {
        // Use a well-known service interface from java.util for testing
        // Since we cannot instantiate ServiceLoader without a service interface,
        // we use java.util.Iterator.class as a dummy service interface
        ServiceLoader<Iterator> loader = ServiceLoader.load(Iterator.class);

        // The iterator should not throw and hasNext should return a boolean
        Iterator<Iterator> it = loader.iterator();
        boolean hasNext = it.hasNext();

        // hasNext returns boolean, no exception expected
        if (hasNext != true && hasNext != false) {
            throw new AssertionError("hasNext() returned non-boolean value");
        }
    }

    // Test that hasNext throws ConcurrentModificationException if reloadCount changes
    private static void testHasNextConcurrentModification() throws Exception {
        // We cannot directly manipulate reloadCount or expectedReloadCount as they are private
        // and no public API to trigger concurrent modification is exposed.
        // So we test that if reload() is called concurrently, hasNext detects it.

        ServiceLoader<Iterator> loader = ServiceLoader.load(Iterator.class);
        Iterator<Iterator> it = loader.iterator();

        // Start iteration to initialize internal state
        it.hasNext();

        // Call reload() to change reloadCount and cause concurrent modification
        loader.reload();

        boolean cmeThrown = false;
        try {
            it.hasNext();
        } catch (ConcurrentModificationException cme) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException after reload() but none thrown");
        }
    }
}
