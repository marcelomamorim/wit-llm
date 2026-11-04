/*
 * @test
 * @summary Test InMemoryCookieStore.remove(URI, HttpCookie) for null argument and normal removal
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.InMemoryCookieStore;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) throws Exception {
        InMemoryCookieStore store = new InMemoryCookieStore();

        // Test 1: remove with null cookie should throw NullPointerException
        boolean npeThrown = false;
        try {
            store.remove(null, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when removing null cookie");
        }

        // Test 2: remove with cookie not present should return false
        HttpCookie cookie1 = new HttpCookie("name", "value");
        URI uri = new URI("http://example.com");
        boolean removed = store.remove(uri, cookie1);
        if (removed) {
            throw new RuntimeException("Expected remove to return false for cookie not present");
        }

        // Test 3: add a cookie and then remove it, should return true
        store.add(uri, cookie1);
        boolean removedAfterAdd = store.remove(uri, cookie1);
        if (!removedAfterAdd) {
            throw new RuntimeException("Expected remove to return true for cookie present");
        }

        // Test 4: remove again the same cookie, should return false
        boolean removedAgain = store.remove(uri, cookie1);
        if (removedAgain) {
            throw new RuntimeException("Expected remove to return false for cookie already removed");
        }
    }
}
