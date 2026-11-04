/*
 * @test
 * @summary Verifies IdentityHashMap values iterator next() returns the exact stored value references
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Iterator;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        singleEntryReturnsSameReference();
        nullValueReturnedAsNull();
        nullKeyValueReturned();
        twoEqualKeysYieldDistinctValues();
    }

    static void singleEntryReturnsSameReference() {
        String k = new String("k");
        String v = new String("v");
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put(k, v);
        Iterator<String> it = m.values().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("hasNext() should be true");
        }
        String got = it.next();
        if (got != v) {
            throw new AssertionError("next() returned a different reference");
        }
        if (it.hasNext()) {
            throw new AssertionError("hasNext() should be false after single element");
        }
    }

    static void nullValueReturnedAsNull() {
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put(new String("nk"), null);
        Iterator<String> it = m.values().iterator();
        String got = it.next();
        if (got != null) {
            throw new AssertionError("next() should return null for null value");
        }
    }

    static void nullKeyValueReturned() {
        String v = new String("nullKeyValue");
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        m.put(null, v);
        String got = m.values().iterator().next();
        if (got != v) {
            throw new AssertionError("next() returned wrong value for null key");
        }
    }

    static void twoEqualKeysYieldDistinctValues() {
        Object v1 = new Object();
        Object v2 = new Object();
        IdentityHashMap<Object, Object> m = new IdentityHashMap<>();
        m.put(new String("same"), v1);
        m.put(new String("same"), v2);
        Iterator<Object> it = m.values().iterator();
        boolean seen1 = false;
        boolean seen2 = false;
        int count = 0;
        while (it.hasNext()) {
            Object got = it.next();
            count++;
            if (got == v1) {
                if (seen1) throw new AssertionError("v1 returned twice");
                seen1 = true;
            } else if (got == v2) {
                if (seen2) throw new AssertionError("v2 returned twice");
                seen2 = true;
            } else {
                throw new AssertionError("next() returned unexpected reference");
            }
        }
        if (count != 2 || !seen1 || !seen2) {
            throw new AssertionError("expected both values exactly once");
        }
    }
}
