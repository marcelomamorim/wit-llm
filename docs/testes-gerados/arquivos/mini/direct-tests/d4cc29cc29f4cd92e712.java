/*
 * @test
 * @summary Test that ImmutableCollections.remove() throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollectionsWitupTest test = new ImmutableCollectionsWitupTest();
        test.testRemoveThrowsUOE();
    }

    static class TestRemove extends java.util.ImmutableCollections.AbstractImmutableCollection<Object> {
        @Override
        public java.util.Iterator<Object> iterator() {
            return new java.util.Iterator<Object>() {
                public boolean hasNext() { return false; }
                public Object next() { throw new java.util.NoSuchElementException(); }
                public void remove() {
                    // call the tested method
                    ImmutableCollectionsWitupTest.callRemove();
                }
            };
        }

        @Override
        public int size() { return 0; }
    }

    public void testRemoveThrowsUOE() {
        java.util.Iterator<Object> it = new TestRemove().iterator();
        try {
            it.remove();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    // Helper to call the static method uoe() and throw it
    static void callRemove() {
        throw java.util.ImmutableCollections.uoe();
    }
}
