/*
 * @test
 * @summary Verify WeakHashMap keySet iterator next() returns the keys of live entries, including the null key
 * @run main WeakHashMapWitupTest
 */

import java.util.Iterator;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsStronglyReferencedKeys();
        testNextReturnsNullKey();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsStronglyReferencedKeys() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object k1 = new Object();
        Object k2 = new Object();
        Object k3 = new Object();
        map.put(k1, "a");
        map.put(k2, "b");
        map.put(k3, "c");

        Iterator<Object> it = map.keySet().iterator();
        boolean[] found = new boolean[3];
        int count = 0;
        while (it.hasNext()) {
            Object k = it.next();
            count++;
            if (k == k1) {
                found[0] = true;
            } else if (k == k2) {
                found[1] = true;
            } else if (k == k3) {
                found[2] = true;
            } else {
                throw new RuntimeException("next() returned unexpected key: " + k);
            }
        }
        check(count == 3, "expected 3 keys from next(), got " + count);
        check(found[0] && found[1] && found[2], "not all keys returned by next()");

        check("a".equals(map.get(k1)), "k1 mapping lost");
        check("b".equals(map.get(k2)), "k2 mapping lost");
        check("c".equals(map.get(k3)), "k3 mapping lost");
    }

    static void testNextReturnsNullKey() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        map.put(null, "x");

        Iterator<Object> it = map.keySet().iterator();
        check(it.hasNext(), "iterator should have one element");
        check(it.next() == null, "next() should return the null key");
        check(!it.hasNext(), "iterator should be exhausted");
        check("x".equals(map.get(null)), "null key mapping lost");
    }
}
