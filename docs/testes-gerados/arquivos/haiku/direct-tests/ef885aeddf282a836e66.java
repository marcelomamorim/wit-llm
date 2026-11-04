/*
 * @test
 * @summary Verifies removeAll() on the default in-memory CookieStore reached through CookieManager
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) throws Exception {
        CookieStore store = new CookieManager().getCookieStore();

        if (store.removeAll()) {
            throw new RuntimeException("removeAll() on empty store must return false");
        }

        URI uri = new URI("http://example.com/");
        store.add(uri, new HttpCookie("a", "1"));
        store.add(uri, new HttpCookie("b", "2"));

        if (store.getCookies().size() != 2) {
            throw new RuntimeException("expected 2 cookies before removeAll()");
        }

        if (!store.removeAll()) {
            throw new RuntimeException("removeAll() must return true when cookies exist");
        }

        if (!store.getCookies().isEmpty()) {
            throw new RuntimeException("cookies remain after removeAll()");
        }

        if (!store.getURIs().isEmpty()) {
            throw new RuntimeException("URIs remain after removeAll()");
        }

        List<HttpCookie> got = store.get(uri);
        if (!got.isEmpty()) {
            throw new RuntimeException("get(uri) returned cookies after removeAll()");
        }

        if (store.removeAll()) {
            throw new RuntimeException("second removeAll() must return false");
        }
    }
}
