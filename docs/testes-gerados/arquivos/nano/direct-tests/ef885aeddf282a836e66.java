/*
 * @test
 * @summary Test the removeAll() method of InMemoryCookieStore.
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.InMemoryCookieStore;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) {
        testRemoveAllWhenEmpty();
        testRemoveAllWhenNotEmpty();
    }

    private static void testRemoveAllWhenEmpty() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        boolean result = store.removeAll();
        if (result != false) {
            throw new AssertionError("Expected false when removing from empty store, but got " + result);
        }
    }

    private static void testRemoveAllWhenNotEmpty() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        // Add a dummy cookie to ensure store is not empty
        try {
            java.net.HttpCookie cookie = java.net.HttpCookie.parse("name=value").get(0);
            store.add(new java.net.URI("http://example.com"), cookie);
        } catch (java.net.URISyntaxException e) {
            throw new RuntimeException(e);
        }
        boolean result = store.removeAll();
        if (result != true) {
            throw new AssertionError("Expected true when removing from non-empty store, but got " + result);
        }
        // Verify store is empty after removal
        boolean resultAfter = store.removeAll();
        if (resultAfter != false) {
            throw new AssertionError("Expected false after removal, but got " + resultAfter);
        }
    }
}
