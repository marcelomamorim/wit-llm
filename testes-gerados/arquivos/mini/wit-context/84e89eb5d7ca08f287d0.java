/*
 * @test
 * @summary Deterministic tests for java.net.InMemoryCookieStore.add(URI,HttpCookie)
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.InMemoryCookieStore;
import java.util.List;

public class InMemoryCookieStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testAddNullCookieThrows();
        testAddValidCookie();
    }

    private static void testAddNullCookieThrows() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        try {
            store.add(null, null);
            throw new RuntimeException("Expected NullPointerException for null cookie");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAddValidCookie() throws URISyntaxException {
        InMemoryCookieStore store = new InMemoryCookieStore();
        URI uri = new URI("http://example.com");
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setDomain("example.com");
        cookie.setMaxAge(10); // non-zero max-age to be added

        store.add(uri, cookie);

        List<HttpCookie> cookies = store.get(uri);
        boolean found = false;
        for (HttpCookie c : cookies) {
            if (c.equals(cookie)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Added cookie not found in store");
        }

        // Add cookie with maxAge=0 should remove it
        HttpCookie cookie2 = new HttpCookie("name2", "value2");
        cookie2.setDomain("example.com");
        cookie2.setMaxAge(0);
        store.add(uri, cookie2);
        List<HttpCookie> cookiesAfter = store.get(uri);
        for (HttpCookie c : cookiesAfter) {
            if (c.equals(cookie2)) {
                throw new RuntimeException("Cookie with maxAge=0 should not be stored");
            }
        }
    }
}
