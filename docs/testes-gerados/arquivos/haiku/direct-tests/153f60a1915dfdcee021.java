/*
 * @test
 * @summary Verifies that IdentityHashMap entry getKey() returns the exact key instance, including null keys and distinct but equal keys.
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testGetKeyReturnsSameInstance();
        testGetKeyReturnsNullForNullKey();
        testGetKeyDistinguishesEqualKeys();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGetKeyReturnsSameInstance() {
        IdentityHashMap<Object, String> m = new IdentityHashMap<>();
        Object k = new Object();
        m.put(k, "v");
        Iterator<Map.Entry<Object, String>> it = m.entrySet().iterator();
        check(it.hasNext(), "iterator should have one entry");
        Map.Entry<Object, String> e = it.next();
        check(e.getKey() == k, "getKey() must return the same key instance");
        check("v".equals(e.getValue()), "getValue() must return the mapped value");
        check(!it.hasNext(), "iterator should have exactly one entry");
    }

    static void testGetKeyReturnsNullForNullKey() {
        IdentityHashMap<Object, String> m = new IdentityHashMap<>();
        m.put(null, "n");
        Iterator<Map.Entry<Object, String>> it = m.entrySet().iterator();
        check(it.hasNext(), "iterator should have one entry");
        Map.Entry<Object, String> e = it.next();
        check(e.getKey() == null, "getKey() must return null for the null key");
        check("n".equals(e.getValue()), "getValue() must return the value for the null key");
    }

    static void testGetKeyDistinguishesEqualKeys() {
        IdentityHashMap<Object, String> m = new IdentityHashMap<>();
        String a = new String("x");
        String b = new String("x");
        m.put(a, "1");
        m.put(b, "2");
        check(m.size() == 2, "equal but distinct keys must both be stored");
        boolean sawA = false;
        boolean sawB = false;
        Iterator<Map.Entry<Object, String>> it = m.entrySet().iterator();
        while (it.hasNext()) {
            Object k = it.next().getKey();
            if (k == a) {
                sawA = true;
            } else if (k == b) {
                sawB = true;
            } else {
                throw new RuntimeException("getKey() returned an unexpected key instance");
            }
        }
        check(sawA, "getKey() must return key a by identity");
        check(sawB, "getKey() must return key b by identity");
    }
}
