/*
 * @test
 * @summary Test sun.util.PreHashedMap.setValue method throws UnsupportedOperationException
 * @run main PreHashedMapWitupTest
 */

public class PreHashedMapWitupTest {

    static class DummyPreHashedMap extends sun.util.PreHashedMap<Object> {
        protected DummyPreHashedMap() {
            super(1, 1, 0, 0);
        }
        @Override
        protected void init(Object[] ht) {
            // no entries
        }
    }

    public static void main(String[] args) {
        DummyPreHashedMap map = new DummyPreHashedMap();
        java.util.Map.Entry<String, Object> entry = new java.util.AbstractMap.SimpleEntry<>("key", "value") {
            @Override
            public Object setValue(Object v) {
                return super.setValue(v);
            }
        };

        boolean thrown = false;
        try {
            entry.setValue("newValue");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by setValue");
        }
    }
}
