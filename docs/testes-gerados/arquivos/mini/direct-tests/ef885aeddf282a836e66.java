/*
 * @test
 * @summary Test InMemoryCookieStore.removeAll() behavior
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.InMemoryCookieStore;
import java.util.List;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) throws Exception {
        InMemoryCookieStore store = new InMemoryCookieStore();

        // Initially empty, removeAll should return false
        if (store.removeAll()) {
            throw new RuntimeException("removeAll() returned true on empty store");
        }

        // Add a cookie
        URI uri = new URI("http://example.com");
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setDomain("example.com");
        cookie.setMaxAge(3600);
        store.add(uri, cookie);

        // After adding, removeAll should return true
        if (!store.removeAll()) {
            throw new RuntimeException("removeAll() returned false on non-empty store");
        }

        // After removeAll, get should return empty list
        List<HttpCookie> cookies = store.get(uri);
        if (cookies == null) {
            throw new RuntimeException("get() returned null after removeAll");
        }
        if (!cookies.isEmpty()) {
            throw new RuntimeException("get() returned non-empty list after removeAll");
        }

        // removeAll again on empty store returns false
        if (store.removeAll()) {
            throw new RuntimeException("removeAll() returned true on empty store after clearing");
        }
    }
}
