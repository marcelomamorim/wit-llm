/*
 * @test
 * @summary Verify that Iterator.remove() on an unmodifiable map entry set throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(base);

        Iterator<Map.Entry<String, Integer>> it = um.entrySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("expected at least one entry");
        }
        it.next();
        try {
            it.remove();
            throw new RuntimeException("remove() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (base.size() != 1 || um.size() != 1) {
            throw new RuntimeException("map was modified by remove()");
        }

        Iterator<Map.Entry<String, Integer>> it2 = um.entrySet().iterator();
        try {
            it2.remove();
            throw new RuntimeException("remove() without next() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
