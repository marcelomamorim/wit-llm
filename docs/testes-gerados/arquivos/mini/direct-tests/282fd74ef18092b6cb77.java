/*
 * @test
 * @summary Test that PreHashedMap.setValue always throws UnsupportedOperationException
 * @run main PreHashedMapWitupTest
 */

import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    static class DummyMap extends PreHashedMap<Object> {
        DummyMap() {
            super(1, 0, 0, 0);
        }
        @Override
        protected void init(Object[] ht) {
            // no entries
        }
    }

    public static void main(String[] args) {
        PreHashedMap<Object> map = new DummyMap();
        try {
            map.entrySet(); // just to ensure map is usable
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception from entrySet", e);
        }

        try {
            map.setValue(null);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
