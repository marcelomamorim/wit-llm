/*
 * @test
 * @summary Test that Collections.put throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testPutUnsupportedOperationException();
    }

    private void testPutUnsupportedOperationException() {
        java.util.Collections coll = new java.util.Collections() {
            @Override
            public Object put(Object key, Object value) {
                return super.put(key, value);
            }
        };

        boolean thrown = false;
        try {
            coll.put("key", "value");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
