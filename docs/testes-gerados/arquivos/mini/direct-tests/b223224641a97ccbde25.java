/*
 * @test
 * @summary Test InMemoryCookieStore.remove(URI, HttpCookie) method behavior
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveNullCookieThrows();
        testRemoveNonExistentCookieReturnsFalse();
        testRemoveExistingCookieReturnsTrue();
    }

    private static void testRemoveNullCookieThrows() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        try {
            store.remove(null, null);
            throw new RuntimeException("Expected NullPointerException for null cookie");
        } catch (NullPointerException e) {
            if (!"cookie is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testRemoveNonExistentCookieReturnsFalse() throws URISyntaxException {
        InMemoryCookieStore store = new InMemoryCookieStore();
        URI uri = new URI("http://example.com");
        HttpCookie cookie = new HttpCookie("name", "value");
        // Not added to store
        boolean removed = store.remove(uri, cookie);
        if (removed) {
            throw new RuntimeException("remove returned true for non-existent cookie");
        }
    }

    private static void testRemoveExistingCookieReturnsTrue() throws URISyntaxException {
        InMemoryCookieStore store = new InMemoryCookieStore();
        URI uri = new URI("http://example.com");
        HttpCookie cookie = new HttpCookie("name", "value");
        store.add(uri, cookie);
        boolean removed = store.remove(uri, cookie);
        if (!removed) {
            throw new RuntimeException("remove returned false for existing cookie");
        }
        // Removing again should return false
        boolean removedAgain = store.remove(uri, cookie);
        if (removedAgain) {
            throw new RuntimeException("remove returned true for already removed cookie");
        }
    }
}
