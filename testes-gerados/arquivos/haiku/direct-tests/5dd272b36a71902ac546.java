/*
 * @test
 * @summary Verifies that the iterator remove() of PreHashedMap throws UnsupportedOperationException
 * @modules java.base/sun.util
 * @run main PreHashedMapWitupTest
 */

import java.util.Iterator;
import java.util.Map;
import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    static class TestMap extends PreHashedMap<String> {
        TestMap() {
            super(1, 1, 0, 0);
        }

        protected void init(Object[] ht) {
            ht[0] = new Object[] { "key", "value" };
        }
    }

    public static void main(String[] args) {
        TestMap map = new TestMap();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Expected at least one entry");
        }
        it.next();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("remove() did not throw UnsupportedOperationException");
        }
    }
}
