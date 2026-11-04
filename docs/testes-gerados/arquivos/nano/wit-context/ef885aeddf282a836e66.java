/*
 * @test
 * @summary Test for InMemoryCookieStore.removeAll() method, including exception path.
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.InMemoryCookieStore;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) {
        testRemoveAllEmpty();
        testRemoveAllWithCookies();
    }

    private static void testRemoveAllEmpty() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        boolean result = false;
        try {
            result = store.removeAll();
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("Unexpected exception in empty store", e);
        }
        if (result != false) {
            throw new AssertionError("Expected false when removing from empty store, but got: " + result);
        }
    }

    private static void testRemoveAllWithCookies() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        // Add a dummy cookie to ensure store is not empty
        try {
            java.net.HttpCookie cookie = java.net.HttpCookie.parse("name=value").get(0);
            store.add(null, cookie);
        } catch (Exception e) {
            throw new AssertionError("Failed to add cookie", e);
        }
        boolean result = false;
        try {
            result = store.removeAll();
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("Unexpected exception when store has cookies", e);
        }
        if (result != true) {
            throw new AssertionError("Expected true when removing cookies, but got: " + result);
        }
    }
}
