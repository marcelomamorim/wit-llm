/*
 * @test
 * @summary Test java.util.ResourceBundle.containsKey method behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;

public class ResourceBundleWitupTest {

    static class TestResourceBundle extends ResourceBundle {
        private final Set<String> keys;

        TestResourceBundle(Set<String> keys) {
            this.keys = keys;
        }

        @Override
        protected Object handleGetObject(String key) {
            if (keys.contains(key)) {
                return "value";
            }
            return null;
        }

        @Override
        public Set<String> handleKeySet() {
            return keys;
        }
    }

    public static void main(String[] args) {
        // Test 1: containsKey returns true for key in this bundle
        Set<String> keys1 = new HashSet<>();
        keys1.add("key1");
        ResourceBundle rb1 = new TestResourceBundle(keys1);
        if (!rb1.containsKey("key1")) {
            throw new AssertionError("Expected containsKey to return true for key1");
        }

        // Test 2: containsKey returns false for key not in this bundle
        if (rb1.containsKey("key2")) {
            throw new AssertionError("Expected containsKey to return false for key2");
        }

        // Test 3: containsKey returns true for key in parent bundle
        Set<String> keysParent = new HashSet<>();
        keysParent.add("parentKey");
        TestResourceBundle parent = new TestResourceBundle(keysParent);
        TestResourceBundle child = new TestResourceBundle(new HashSet<String>());
        child.parent = parent;
        if (!child.containsKey("parentKey")) {
            throw new AssertionError("Expected containsKey to return true for parentKey in parent");
        }

        // Test 4: containsKey returns false for key not in this or parent
        if (child.containsKey("missingKey")) {
            throw new AssertionError("Expected containsKey to return false for missingKey");
        }

        // Test 5: containsKey throws NullPointerException for null key
        boolean npeThrown = false;
        try {
            rb1.containsKey(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }
}
