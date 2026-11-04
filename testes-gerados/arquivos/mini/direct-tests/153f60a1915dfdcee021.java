/*
 * @test
 * @summary Test IdentityHashMap.getKey() method behavior
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testGetKeyReturnsCorrectKey();
    }

    private static void testGetKeyReturnsCorrectKey() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        Object key = new Object();
        Object value = new Object();
        map.put(key, value);

        // Use iterator to get an Entry and call getKey()
        IdentityHashMap.Entry<Object, Object> entry = null;
        for (IdentityHashMap.Entry<Object, Object> e : map.entrySet()) {
            entry = e;
            break;
        }

        if (entry == null) {
            throw new AssertionError("Entry should not be null");
        }

        Object returnedKey = entry.getKey();

        if (returnedKey != key) {
            throw new AssertionError("getKey() did not return the original key instance");
        }
    }
}
