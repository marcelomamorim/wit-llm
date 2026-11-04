/*
 * @test
 * @summary Behavioral tests for InMemoryCookieStore.add via the public CookieManager/CookieStore API
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class InMemoryCookieStoreWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static CookieStore newStore() {
        return new CookieManager().getCookieStore();
    }

    static void testNullCookieThrowsNpe() {
        CookieStore store = newStore();
        boolean thrown = false;
        try {
            store.add(URI.create("http://example.com/"), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null cookie");
    }

    static void testNullUriAcceptedAndStored() {
        CookieStore store = newStore();
        HttpCookie c = new HttpCookie("n1", "v1");
        store.add(null, c);
        List<HttpCookie> all = store.getCookies();
        check(all.size() == 1, "expected 1 cookie, got " + all.size());
        check(all.get(0).getValue().equals("v1"), "unexpected value");
    }

    static void testZeroMaxAgeNotStored() {
        CookieStore store = newStore();
        HttpCookie c = new HttpCookie("n2", "v2");
        c.setMaxAge(0);
        store.add(URI.create("http://example.com/"), c);
        check(store.getCookies().isEmpty(), "cookie with maxAge 0 must not be stored");
    }

    static void testReplaceSameCookie() {
        CookieStore store = newStore();
        URI uri = URI.create("http://example.com/");
        store.add(uri, new HttpCookie("n3", "old"));
        store.add(uri, new HttpCookie("n3", "new"));
        List<HttpCookie> all = store.getCookies();
        check(all.size() == 1, "expected 1 cookie after replace, got " + all.size());
        check(all.get(0).getValue().equals("new"), "expected replaced value 'new'");
    }

    static void testZeroMaxAgeRemovesExisting() {
        CookieStore store = newStore();
        URI uri = URI.create("http://example.com/");
        store.add(uri, new HttpCookie("n4", "v4"));
        HttpCookie expire = new HttpCookie("n4", "v4");
        expire.setMaxAge(0);
        store.add(uri, expire);
        check(store.getCookies().isEmpty(), "maxAge 0 add must remove existing cookie");
    }

    static void testMultipleDistinctCookies() {
        CookieStore store = newStore();
        URI uri = URI.create("http://example.com/");
        store.add(uri, new HttpCookie("a", "1"));
        store.add(uri, new HttpCookie("b", "2"));
        check(store.getCookies().size() == 2, "expected 2 distinct cookies");
    }

    public static void main(String[] args) {
        testNullCookieThrowsNpe();
        testNullUriAcceptedAndStored();
        testZeroMaxAgeNotStored();
        testReplaceSameCookie();
        testZeroMaxAgeRemovesExisting();
        testMultipleDistinctCookies();
    }
}
