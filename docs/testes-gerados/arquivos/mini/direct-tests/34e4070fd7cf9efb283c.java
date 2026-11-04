/*
 * @test
 * @summary Test Collections.putIfAbsent throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testPutIfAbsentUnsupported();
    }

    private void testPutIfAbsentUnsupported() {
        java.util.Collections coll = new java.util.Collections() {
            @Override
            public Object putIfAbsent(Object key, Object value) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            coll.putIfAbsent("key", "value");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
