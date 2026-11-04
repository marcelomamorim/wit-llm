/*
 * @test
 * @summary Test that Collections.replace throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testReplaceUnsupported();
    }

    private void testReplaceUnsupported() {
        java.util.Collections col = new java.util.Collections() {
            @Override
            public Object replace(Object key, Object value) {
                return super.replace(key, value);
            }
        };

        try {
            col.replace("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
