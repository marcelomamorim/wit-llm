/*
 * @test
 * @summary Verify CookieManager default in-memory CookieStore removeAll returns observable boolean results and clears cookies
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) throws Exception {
        CookieManager manager = new CookieManager();
        CookieStore store = manager.getCookieStore();
        URI uri = new URI("http://example.com/");

        if (store.removeAll()) {
            throw new RuntimeException("removeAll on empty store should return false");
        }

        HttpCookie cookie = new HttpCookie("name", "value");
        store.add(uri, cookie);

        if (store.get(uri).isEmpty()) {
            throw new RuntimeException("cookie should be retrievable before removeAll");
        }

        if (!store.removeAll()) {
            throw new RuntimeException("removeAll on non-empty store should return true");
        }

        List<HttpCookie> remaining = store.getCookies();
        if (!remaining.isEmpty()) {
            throw new RuntimeException("getCookies should be empty after removeAll");
        }

        if (!store.get(uri).isEmpty()) {
            throw new RuntimeException("get(uri) should be empty after removeAll");
        }

        if (store.removeAll()) {
            throw new RuntimeException("second removeAll on empty store should return false");
        }
    }
}
