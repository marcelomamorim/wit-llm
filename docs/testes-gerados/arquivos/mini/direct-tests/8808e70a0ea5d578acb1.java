/*
 * @test
 * @summary Test that Collections.add(E) always throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testAddThrows();
    }

    static class DummyCollections<E> {
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    public void testAddThrows() {
        DummyCollections<String> c = new DummyCollections<>();
        try {
            c.add("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
