/* @test    @summary Testa o método remove de InMemoryCookieStore para comportamentos observáveis.    @run main InMemoryCookieStoreWitupTest */

import java.net.URI;
import java.net.HttpCookie;
import java.net.InMemoryCookieStore;
import java.util.List;

public class InMemoryCookieStoreWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullCookie();
        testRemoveWithValidCookie();
    }

    public static void testRemoveWithNullCookie() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        try {
            store.remove(URI.create("http://example.com"), null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testRemoveWithValidCookie() {
        InMemoryCookieStore store = new InMemoryCookieStore();
        URI uri = URI.create("http://example.com");
        HttpCookie cookie = new HttpCookie("name", "value");
        store.add(uri, cookie);
        boolean removed;
        try {
            removed = store.remove(uri, cookie);
            if (removed) {
                System.out.println("PASS: Cookie removed successfully");
            } else {
                System.out.println("FAIL: Cookie was not removed");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
