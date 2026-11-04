/*
 * @test
 * @summary PreHashedMap iterator remove() must throw UnsupportedOperationException and leave the entry in place
 * @modules java.base/sun.util
 * @run main PreHashedMapWitupTest
 */

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    static class TestMap extends PreHashedMap<String> {
        TestMap() {
            super(1, 1, 0, 0);
        }

        @Override
        protected void init(Object[] ht) {
            ht[0] = new Object[] { "k", "v" };
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static int countEntries(Map<String, String> m) {
        int n = 0;
        Iterator<Map.Entry<String, String>> it = m.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            n++;
        }
        return n;
    }

    static void testRemoveAfterNextThrows() {
        TestMap m = new TestMap();
        Iterator<Map.Entry<String, String>> it = m.entrySet().iterator();
        check(it.hasNext(), "iterator should have one entry");
        Map.Entry<String, String> e = it.next();
        check("k".equals(e.getKey()), "unexpected key: " + e.getKey());
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        check(thrown, "remove() after next() should throw UnsupportedOperationException");
        check(countEntries(m) == 1, "entry must not be removed");
    }

    static void testRemoveBeforeNextThrows() {
        TestMap m = new TestMap();
        Iterator<Map.Entry<String, String>> it = m.entrySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        check(thrown, "remove() before next() should throw UnsupportedOperationException");
    }

    public static void main(String[] args) {
        testRemoveAfterNextThrows();
        testRemoveBeforeNextThrows();
    }
}
