/*
 * @test
 * @summary Checks public CookieStore remove behavior observable through CookieManager default store
 * @run main InMemoryCookieStoreWitupTest
 */

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveNullCookieThrowsNPE();
        testRemoveAddedCookieReturnsTrue();
        testRemoveSameCookieTwiceReturnsFalseSecondTime();
        testRemoveNeverAddedCookieReturnsFalse();
    }

    private static CookieStore newStore() {
        return new CookieManager().getCookieStore();
    }

    private static void testRemoveNullCookieThrowsNPE() throws Exception {
        CookieStore store = newStore();
        URI uri = new URI("http://example.com/");
        boolean thrown = false;
        try {
            store.remove(uri, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null cookie");
        }
    }

    private static void testRemoveAddedCookieReturnsTrue() throws Exception {
        CookieStore store = newStore();
        URI uri = new URI("http://example.com/");
        HttpCookie cookie = new HttpCookie("name", "value");
        store.add(uri, cookie);
        if (!store.remove(uri, cookie)) {
            throw new RuntimeException("remove of added cookie should return true");
        }
    }

    private static void testRemoveSameCookieTwiceReturnsFalseSecondTime() throws Exception {
        CookieStore store = newStore();
        URI uri = new URI("http://example.com/");
        HttpCookie cookie = new HttpCookie("name", "value");
        store.add(uri, cookie);
        if (!store.remove(uri, cookie)) {
            throw new RuntimeException("first remove should return true");
        }
        if (store.remove(uri, cookie)) {
            throw new RuntimeException("second remove should return false");
        }
    }

    private static void testRemoveNeverAddedCookieReturnsFalse() throws Exception {
        CookieStore store = newStore();
        URI uri = new URI("http://example.com/");
        HttpCookie cookie = new HttpCookie("absent", "value");
        if (store.remove(uri, cookie)) {
            throw new RuntimeException("remove of absent cookie should return false");
        }
    }
}
