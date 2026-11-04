/*
 * @test
 * @summary Test IdentityHashMap next() method behavior
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Iterator;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsCorrectKey();
    }

    private static void testNextReturnsCorrectKey() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, "value1");
        map.put(key2, "value2");

        Iterator<String> it = map.keySet().iterator();

        // next() should return keys in iteration order (reference equality)
        String first = it.next();
        String second = it.next();

        // Both keys must be one of the inserted keys
        if (!(first == key1 || first == key2)) {
            throw new AssertionError("First key returned by next() is not one of the inserted keys");
        }
        if (!(second == key1 || second == key2)) {
            throw new AssertionError("Second key returned by next() is not one of the inserted keys");
        }

        // The two keys returned must be distinct references
        if (first == second) {
            throw new AssertionError("next() returned the same key twice");
        }

        // No more elements
        if (it.hasNext()) {
            throw new AssertionError("Iterator has more elements than expected");
        }
    }
}
