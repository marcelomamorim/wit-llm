/*
 * @test
 * @summary Test java.util.ResourceBundle.containsKey(String) method
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;

public class ResourceBundleWitupTest {

    // Minimal subclass to allow instantiation and override handleKeySet
    static class TestResourceBundle extends ResourceBundle {
        private final Set<String> keys;

        TestResourceBundle(Set<String> keys) {
            this.keys = keys;
        }

        @Override
        protected Object handleGetObject(String key) {
            if (keys.contains(key)) {
                return key + "-value";
            }
            return null;
        }

        @Override
        public Set<String> handleKeySet() {
            return keys;
        }
    }

    public static void main(String[] args) {
        testContainsKeyWithNull();
        testContainsKeyWithPresentKey();
        testContainsKeyWithAbsentKey();
        testContainsKeyWithParent();
    }

    private static void testContainsKeyWithNull() {
        ResourceBundle rb = new TestResourceBundle(new HashSet<String>());
        try {
            rb.containsKey(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testContainsKeyWithPresentKey() {
        Set<String> keys = new HashSet<String>();
        keys.add("key1");
        ResourceBundle rb = new TestResourceBundle(keys);
        if (!rb.containsKey("key1")) {
            throw new RuntimeException("containsKey should return true for existing key");
        }
    }

    private static void testContainsKeyWithAbsentKey() {
        Set<String> keys = new HashSet<String>();
        keys.add("key1");
        ResourceBundle rb = new TestResourceBundle(keys);
        if (rb.containsKey("key2")) {
            throw new RuntimeException("containsKey should return false for non-existing key");
        }
    }

    private static void testContainsKeyWithParent() {
        Set<String> parentKeys = new HashSet<String>();
        parentKeys.add("parentKey");
        TestResourceBundle parent = new TestResourceBundle(parentKeys);

        Set<String> childKeys = new HashSet<String>();
        childKeys.add("childKey");
        TestResourceBundle child = new TestResourceBundle(childKeys);

        // Set parent
        child.parent = parent;

        if (!child.containsKey("childKey")) {
            throw new RuntimeException("containsKey should return true for child's own key");
        }
        if (!child.containsKey("parentKey")) {
            throw new RuntimeException("containsKey should return true for parent's key");
        }
        if (child.containsKey("absentKey")) {
            throw new RuntimeException("containsKey should return false for absent key");
        }
    }
}
