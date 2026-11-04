/* @test    @summary Test Collections.replaceAll method for UnsupportedOperationException handling    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllThrows();
    }

    public static void testReplaceAllThrows() {
        try {
            Collections.replaceAll(new DummyCollection<>(), null);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }

    static class DummyCollection<E> extends java.util.AbstractCollection<E> {
        @Override
        public java.util.Iterator<E> iterator() {
            return java.util.Collections.emptyIterator();
        }

        @Override
        public int size() {
            return 0;
        }
    }
}
