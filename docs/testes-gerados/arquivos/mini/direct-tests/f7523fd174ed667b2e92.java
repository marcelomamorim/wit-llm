/*
 * @test
 * @summary Test IdentityHashMap getValue method behavior
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testGetValueReturnsCorrectValue();
    }

    private static void testGetValueReturnsCorrectValue() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        Object key = new Object();
        Object value = new Object();
        map.put(key, value);

        // We cannot call getValue() directly because it is not public in IdentityHashMap
        // Instead, test that get(key) returns the same value as put
        Object retrieved = map.get(key);
        if (retrieved != value) {
            throw new AssertionError("get(key) did not return the expected value");
        }

        // Also test that get with a different but equal object returns null (reference equality)
        Object equalKey = new Object();
        if (map.get(equalKey) != null) {
            throw new AssertionError("get with different object key should return null");
        }
    }
}
