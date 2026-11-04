/*
 * @test
 * @summary Verify that the iterator returned by PreHashedMap.entrySet() throws UnsupportedOperationException on remove()
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
            ht[0] = new Object[] { "k", "v" };
        }
    }

    public static void main(String[] args) {
        TestMap map = new TestMap();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from remove()");
        }
    }
}
