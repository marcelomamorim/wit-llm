/*
 * @test
 * @summary Test ResourceBundle.containsKey(String key) for various scenarios
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.HashSet;
import java.util.Set;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNull();
        testContainsKeyKeyPresent();
        testContainsKeyKeyAbsent();
        testContainsKeyWithParent();
    }

    static class DummyResourceBundle extends ResourceBundle {
        private final Set<String> keys;
        private final ResourceBundle parent;

        DummyResourceBundle(Set<String> keys, ResourceBundle parent) {
            this.keys = keys;
            this.parent = parent;
        }

        @Override
        protected Object handleGetObject(String key) {
            if (keys.contains(key)) {
                return key;
            }
            return null;
        }

        @Override
        public Set<String> handleKeySet() {
            return keys;
        }

        @Override
        public ResourceBundle getParent() {
            return parent;
        }
    }

    static void testContainsKeyWithNull() {
        ResourceBundle rb = new DummyResourceBundle(new HashSet<>(), null);
        boolean exceptionThrown = false;
        try {
            rb.containsKey(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null key";
    }

    static void testContainsKeyKeyPresent() {
        Set<String> keys = new HashSet<>();
        keys.add("testKey");
        ResourceBundle rb = new DummyResourceBundle(keys, null);
        boolean result = rb.containsKey("testKey");
        assert result : "Key 'testKey' should be present";
    }

    static void testContainsKeyKeyAbsent() {
        Set<String> keys = new HashSet<>();
        keys.add("existingKey");
        ResourceBundle rb = new DummyResourceBundle(keys, null);
        boolean result = rb.containsKey("absentKey");
        assert !result : "Key 'absentKey' should not be present";
    }

    static void testContainsKeyWithParent() {
        Set<String> parentKeys = new HashSet<>();
        parentKeys.add("parentKey");
        ResourceBundle parent = new DummyResourceBundle(parentKeys, null);
        Set<String> childKeys = new HashSet<>();
        childKeys.add("childKey");
        ResourceBundle child = new DummyResourceBundle(childKeys, parent);
        boolean resultParent = child.containsKey("parentKey");
        boolean resultChild = child.containsKey("childKey");
        boolean resultAbsent = child.containsKey("absentKey");
        assert resultParent : "Parent key should be found in parent";
        assert resultChild : "Child key should be found in child";
        assert !resultAbsent : "Absent key should not be found";
    }
}
