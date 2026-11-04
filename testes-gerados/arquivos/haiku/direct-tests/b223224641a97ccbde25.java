/*
 * @test
 * @summary Verify InMemoryCookieStore.remove(URI, HttpCookie) through the public CookieManager store
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) throws Exception {
        CookieStore store = new CookieManager().getCookieStore();
        URI uri = new URI("http://example.com/");

        HttpCookie c = new HttpCookie("a", "1");
        store.add(uri, c);
        if (!store.remove(uri, c)) {
            throw new AssertionError("remove of stored cookie should return true");
        }
        if (store.remove(uri, c)) {
            throw new AssertionError("second remove of same cookie should return false");
        }

        HttpCookie c2 = new HttpCookie("b", "2");
        store.add(uri, c2);
        if (!store.remove(null, c2)) {
            throw new AssertionError("uri is not used by remove; null uri should still remove");
        }

        HttpCookie absent = new HttpCookie("absent", "x");
        if (store.remove(uri, absent)) {
            throw new AssertionError("remove of never-added cookie should return false");
        }

        boolean npe = false;
        try {
            store.remove(uri, null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("null cookie should cause NullPointerException");
        }
    }
}
