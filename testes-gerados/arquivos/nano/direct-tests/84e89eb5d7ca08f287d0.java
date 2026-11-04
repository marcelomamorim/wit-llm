/* @test    @summary Test InMemoryCookieStore.add method for null cookie and maxAge behavior    @run main InMemoryCookieStoreWitupTest */

import java.net.URI;
import java.net.HttpCookie;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) throws URISyntaxException {
        testAddNullCookie();
        testAddCookieWithMaxAgeZero();
        testAddCookieWithMaxAgeNonZero();
    }

    static void testAddNullCookie() {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        try {
            store.add(new URI("http://example.com"), null);
            throw new AssertionError("Expected NullPointerException for null cookie");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testAddCookieWithMaxAgeZero() throws URISyntaxException {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setMaxAge(0);
        URI uri = new URI("http://example.com");
        store.add(uri, cookie);
        List<HttpCookie> cookies = store.get(uri);
        if (cookies != null && cookies.contains(cookie)) {
            throw new AssertionError("Cookie with maxAge=0 should not be added");
        }
    }

    static void testAddCookieWithMaxAgeNonZero() throws URISyntaxException {
        java.net.InMemoryCookieStore store = new java.net.InMemoryCookieStore();
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setMaxAge(100);
        URI uri = new URI("http://example.com");
        store.add(uri, cookie);
        List<HttpCookie> cookies = store.get(uri);
        if (cookies == null || !cookies.contains(cookie)) {
            throw new AssertionError("Cookie with maxAge>0 should be added");
        }
    }
}
