/*
 * @test
 * @summary Test InMemoryCookieStore.removeAll() behavior
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.InMemoryCookieStore;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) {
        InMemoryCookieStore store = new InMemoryCookieStore();

        // Initially empty, removeAll should return false
        if (store.removeAll() != false) {
            throw new AssertionError("removeAll() on empty store should return false");
        }

        // Add a cookie to the store to make it non-empty
        // Since HttpCookie and URI constructors are public, create a simple cookie
        java.net.HttpCookie cookie = new java.net.HttpCookie("name", "value");
        cookie.setMaxAge(1000); // non-zero max-age to ensure it is added
        java.net.URI uri = null;
        try {
            uri = new java.net.URI("http://example.com");
        } catch (java.net.URISyntaxException e) {
            throw new AssertionError("URI syntax error", e);
        }

        store.add(uri, cookie);

        // Now removeAll should return true
        if (store.removeAll() != true) {
            throw new AssertionError("removeAll() on non-empty store should return true");
        }

        // After removeAll, store should be empty again
        if (store.removeAll() != false) {
            throw new AssertionError("removeAll() after clearing should return false");
        }
    }
}
