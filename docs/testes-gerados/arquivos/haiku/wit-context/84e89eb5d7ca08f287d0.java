/*
 * @test
 * @summary Witup-generated checks for CookieStore.add through the public CookieManager default store
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCookieThrowsNPE();
        testNullUriStoresCookie();
        testZeroMaxAgeNotStored();
        testSameCookieReplaced();
    }

    static void testNullCookieThrowsNPE() {
        CookieStore store = new CookieManager().getCookieStore();
        try {
            store.add(URI.create("http://example.com/"), null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null cookie");
    }

    static void testNullUriStoresCookie() {
        CookieStore store = new CookieManager().getCookieStore();
        HttpCookie c = new HttpCookie("a", "1");
        store.add(null, c);
        if (!containsName(store.getCookies(), "a")) {
            throw new RuntimeException("cookie added with null uri not found");
        }
    }

    static void testZeroMaxAgeNotStored() {
        CookieStore store = new CookieManager().getCookieStore();
        HttpCookie c = new HttpCookie("b", "2");
        c.setMaxAge(0);
        store.add(URI.create("http://example.com/"), c);
        if (containsName(store.getCookies(), "b")) {
            throw new RuntimeException("cookie with max-age 0 must not be stored");
        }
    }

    static void testSameCookieReplaced() {
        CookieStore store = new CookieManager().getCookieStore();
        URI uri = URI.create("http://example.com/");
        store.add(uri, new HttpCookie("x", "1"));
        store.add(uri, new HttpCookie("x", "2"));
        List<HttpCookie> cookies = store.getCookies();
        int count = 0;
        String value = null;
        for (HttpCookie c : cookies) {
            if ("x".equals(c.getName())) {
                count++;
                value = c.getValue();
            }
        }
        if (count != 1 || !"2".equals(value)) {
            throw new RuntimeException("expected single replaced cookie, count=" + count + " value=" + value);
        }
    }

    static boolean containsName(List<HttpCookie> cookies, String name) {
        for (HttpCookie c : cookies) {
            if (name.equals(c.getName())) {
                return true;
            }
        }
        return false;
    }
}
