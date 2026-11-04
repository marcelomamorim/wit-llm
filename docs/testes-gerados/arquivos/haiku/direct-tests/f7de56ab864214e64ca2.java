/*
 * @test
 * @summary ResourceBundle.containsKey: own keys, parent chain, null values and null key
 * @run main ResourceBundleWitupTest
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ResourceBundleWitupTest {

    static class MapBundle extends ResourceBundle {
        private final Map<String, Object> map = new HashMap<>();

        MapBundle(ResourceBundle parent) {
            if (parent != null) {
                setParent(parent);
            }
        }

        void put(String key, Object value) {
            map.put(key, value);
        }

        @Override
        protected Object handleGetObject(String key) {
            return map.get(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(map.keySet());
        }
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        MapBundle parent = new MapBundle(null);
        parent.put("p", "parentValue");

        MapBundle child = new MapBundle(parent);
        child.put("c", "childValue");
        child.put("n", null);

        check(child.containsKey("c"), "own key must be found");
        check(child.containsKey("p"), "key only in parent must be found through parent chain");
        check(!child.containsKey("n"), "key with null value must not be reported as contained");
        check(!child.containsKey("missing"), "absent key must not be found");
        check(!parent.containsKey("c"), "child key must not be visible from parent");

        try {
            child.containsKey(null);
            throw new RuntimeException("containsKey(null) must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
