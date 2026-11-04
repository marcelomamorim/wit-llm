/*
 * @test
 * @summary Test java.util.Collections.get(int) method behavior
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {

    static class SingleElementCollection<E> {
        private final E element;

        SingleElementCollection(E element) {
            this.element = element;
        }

        public E get(int index) {
            if (index != 0)
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
            return element;
        }
    }

    public static void main(String[] args) {
        SingleElementCollection<String> col = new SingleElementCollection<>("test");

        // Test get(0) returns the element
        String value = col.get(0);
        if (!"test".equals(value)) {
            throw new AssertionError("Expected 'test' but got: " + value);
        }

        // Test get with negative index throws IndexOutOfBoundsException
        try {
            col.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // Test get with index > 0 throws IndexOutOfBoundsException
        try {
            col.get(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
