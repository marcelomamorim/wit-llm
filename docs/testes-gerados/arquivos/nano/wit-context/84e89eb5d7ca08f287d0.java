/*
 * @test
 * @summary Tests for java.net.InMemoryCookieStore.add(URI, HttpCookie)
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.URI;
import java.net.HttpCookie;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) throws URISyntaxException {
        testAddNullCookie();
        testAddValidCookie();
    }

    private static void testAddNullCookie() {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        boolean exceptionThrown = false;
        try {
            store.add(new URI("http://example.com"), null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null cookie");
        }
    }

    private static void testAddValidCookie() throws URISyntaxException {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        URI uri = new URI("http://example.com");
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setMaxAge(100);
        cookie.setDomain("example.com");
        store.add(uri, cookie);
        List<HttpCookie> cookies = store.get(uri);
        boolean found = false;
        for (HttpCookie c : cookies) {
            if (c.getName().equals("name") && c.getValue().equals("value")) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Cookie not found after add");
        }
    }
}
