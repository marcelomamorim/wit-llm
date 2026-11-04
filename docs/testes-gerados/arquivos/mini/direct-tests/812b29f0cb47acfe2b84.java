/*
 * @test
 * @summary Test that Collections.remove() throws IllegalStateException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testRemoveThrows();
    }

    private static class TestRemove extends java.util.Collections {
        @Override
        public void remove() {
            super.remove();
        }
    }

    public void testRemoveThrows() {
        java.util.Collections c = new java.util.Collections() {
            @Override
            public void remove() {
                throw new IllegalStateException();
            }
        };

        boolean thrown = false;
        try {
            c.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException not thrown");
        }
    }
}
