/*
 * @test
 * @summary Verify ListResourceBundle.handleGetObject lookup, missing keys, null key handling and lazy loading
 * @run main ListResourceBundleWitupTest
 */

import java.util.ListResourceBundle;

public class ListResourceBundleWitupTest {

    static int calls = 0;

    static class Bundle extends ListResourceBundle {
        @Override
        protected Object[][] getContents() {
            calls++;
            return new Object[][] {
                {"s1", "value1"},
                {"s2", Integer.valueOf(42)}
            };
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Bundle b = new Bundle();

        check("value1".equals(b.handleGetObject("s1")), "s1 should map to value1");
        check(Integer.valueOf(42).equals(b.handleGetObject("s2")), "s2 should map to 42");
        check(b.handleGetObject("missing") == null, "missing key should return null");
        check(b.handleGetObject("S1") == null, "keys are case-sensitive");

        boolean npe = false;
        try {
            b.handleGetObject(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null key should throw NullPointerException");

        calls = 0;
        Bundle lazy = new Bundle();
        check(calls == 0, "getContents should not be called on construction");
        check("value1".equals(lazy.handleGetObject("s1")), "lookup after lazy load");
        check(calls == 1, "getContents should be called once on first lookup");
        check("value1".equals(lazy.handleGetObject("s1")), "repeated lookup");
        check(calls == 1, "lookup table should be loaded only once");
    }
}
