/* @test    @summary Test handleGetObject method of ListResourceBundle for existing and null keys    @run main ListResourceBundleWitupTest */

import java.util.ListResourceBundle;

public class ListResourceBundleWitupTest {

    public static void main(String[] args) {
        TestBundle bundle = new TestBundle();
        Object value = bundle.handleGetObject("existingKey");
        assert value != null : "Expected non-null for existing key";
        assert value.equals("value1") : "Unexpected value for existing key";

        Object nullResult = bundle.handleGetObject(null);
        assert false : "Expected NullPointerException for null key";
    }

    static class TestBundle extends ListResourceBundle {
        private Object[][] contents = {
            {"existingKey", "value1"}
        };
        private java.util.Hashtable<String, Object> lookup;

        @Override
        protected Object[][] getContents() {
            return contents;
        }

        public Object handleGetObject(String key) {
            if (lookup == null) {
                loadLookup();
            }
            if (key == null) {
                throw new NullPointerException();
            }
            return lookup.get(key);
        }

        private void loadLookup() {
            lookup = new java.util.Hashtable<>();
            for (Object[] pair : getContents()) {
                lookup.put((String) pair[0], pair[1]);
            }
        }
    }
}
