/*
 * @test
 * @summary Test UnsupportedOperationException from Collections.add method
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testAddThrows();
    }

    private void testAddThrows() {
        java.util.Collections coll = new java.util.Collections() {
            @Override
            public void add(Object e) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            coll.add("test");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
