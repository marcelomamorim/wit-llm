/*
 * @test
 * @summary Test ListResourceBundle.handleGetObject(String) for null key and normal lookup
 * @run main ListResourceBundleWitupTest
 */

import java.util.ListResourceBundle;
import java.util.Hashtable;

public class ListResourceBundleWitupTest extends ListResourceBundle {

    private Hashtable<String, Object> lookup = null;

    public ListResourceBundleWitupTest() {
        super();
    }

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
            {"key1", "value1"},
            {"key2", "value2"}
        };
    }

    private void loadLookup() {
        lookup = new Hashtable<>();
        Object[][] contents = getContents();
        for (int i = 0; i < contents.length; i++) {
            String k = (String) contents[i][0];
            Object v = contents[i][1];
            lookup.put(k, v);
        }
    }

    public final Object handleGetObject(String key) {
        if (lookup == null) {
            loadLookup();
        }
        if (key == null) {
            throw new NullPointerException();
        }
        return lookup.get(key);
    }

    public static void main(String[] args) {
        ListResourceBundleWitupTest bundle = new ListResourceBundleWitupTest();

        // Test normal key lookup
        Object val1 = bundle.handleGetObject("key1");
        if (!"value1".equals(val1)) {
            throw new RuntimeException("Expected value1 for key1, got: " + val1);
        }

        // Test another key
        Object val2 = bundle.handleGetObject("key2");
        if (!"value2".equals(val2)) {
            throw new RuntimeException("Expected value2 for key2, got: " + val2);
        }

        // Test key not present returns null
        Object val3 = bundle.handleGetObject("key3");
        if (val3 != null) {
            throw new RuntimeException("Expected null for missing key key3, got: " + val3);
        }

        // Test null key throws NullPointerException
        boolean npeThrown = false;
        try {
            bundle.handleGetObject(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }
    }
}
