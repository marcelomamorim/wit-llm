/*
 * @test
 * @summary Witup-generated tests for ListResourceBundle.handleGetObject null key handling and lookup behavior
 * @run main ListResourceBundleWitupTest
 */

public class ListResourceBundleWitupTest {

    static class TestBundle extends ListResourceBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] {
                {"k1", "v1"},
                {"k2", Integer.valueOf(2)}
            };
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        // Expath: null key with lookup initialized after first access must throw NPE
        TestBundle bundle = new TestBundle();
        check("v1".equals(bundle.handleGetObject("k1")), "existing key should return its value");
        check(Integer.valueOf(2).equals(bundle.handleGetObject("k2")), "existing non-string value should be returned");
        check(bundle.handleGetObject("missing") == null, "missing key should return null");

        boolean npeAfterInit = false;
        try {
            bundle.handleGetObject(null);
        } catch (NullPointerException e) {
            npeAfterInit = true;
        }
        check(npeAfterInit, "null key after lookup initialization should throw NullPointerException");

        // Expath: null key on a fresh instance (lookup == null triggers loadLookup first)
        TestBundle fresh = new TestBundle();
        boolean npeFresh = false;
        try {
            fresh.handleGetObject(null);
        } catch (NullPointerException e) {
            npeFresh = true;
        }
        check(npeFresh, "null key on fresh bundle should throw NullPointerException");

        // Lookup must remain usable after a rejected null key
        check("v1".equals(fresh.handleGetObject("k1")), "bundle should still resolve keys after null key rejection");
    }
}
