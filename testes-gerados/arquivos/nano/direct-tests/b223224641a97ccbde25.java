/*
 * @test
 * @summary Test the remove(URI, HttpCookie) method of InMemoryCookieStore.
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.URI;
import java.net.HttpCookie;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) throws URISyntaxException {
        testRemoveExistingCookie();
        testRemoveNonExistingCookie();
        testRemoveNullCookie();
    }

    static void testRemoveExistingCookie() throws URISyntaxException {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        URI uri = new URI("http://example.com");
        HttpCookie cookie = new HttpCookie("session", "abc123");
        store.add(uri, cookie);
        boolean removed = store.remove(uri, cookie);
        if (!removed) {
            throw new AssertionError("Expected cookie to be removed successfully.");
        }
        List<HttpCookie> cookies = store.get(uri);
        if (cookies.contains(cookie)) {
            throw new AssertionError("Cookie still present after removal.");
        }
    }

    static void testRemoveNonExistingCookie() throws URISyntaxException {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        URI uri = new URI("http://example.com");
        HttpCookie cookie = new HttpCookie("nonexistent", "value");
        boolean removed = store.remove(uri, cookie);
        if (removed) {
            throw new AssertionError("Expected removal to fail for non-existing cookie.");
        }
    }

    static void testRemoveNullCookie() throws URISyntaxException {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        URI uri = new URI("http://example.com");
        try {
            store.remove(uri, null);
            throw new AssertionError("Expected NullPointerException for null cookie.");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
