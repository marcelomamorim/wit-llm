/*
 * @test
 * @summary Test that PreHashedMap.remove() throws UnsupportedOperationException
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
            map.remove();
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
