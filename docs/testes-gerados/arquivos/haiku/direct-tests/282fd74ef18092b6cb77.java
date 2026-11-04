/*
 * @test
 * @summary Verifies that entries of a PreHashedMap reject setValue with UnsupportedOperationException and keep their original value
 * @modules java.base/sun.util
 * @run main PreHashedMapWitupTest
 */

import sun.util.PreHashedMap;
import java.util.Iterator;
import java.util.Map;

public class PreHashedMapWitupTest {

    static final class OneEntryMap extends PreHashedMap<String> {
        OneEntryMap() {
            super(1, 1, 0, 0);
        }

        @Override
        protected void init(Object[] ht) {
            ht[0] = new Object[] { "key", "value" };
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new OneEntryMap();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("expected one entry");
        }
        Map.Entry<String, String> e = it.next();
        if (it.hasNext()) {
            throw new RuntimeException("expected exactly one entry");
        }
        if (!"key".equals(e.getKey()) || !"value".equals(e.getValue())) {
            throw new RuntimeException("unexpected entry: " + e.getKey() + "=" + e.getValue());
        }

        boolean thrown = false;
        try {
            e.setValue("other");
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue did not throw UnsupportedOperationException");
        }
        if (!"value".equals(map.get("key"))) {
            throw new RuntimeException("value changed after rejected setValue");
        }
    }
}
