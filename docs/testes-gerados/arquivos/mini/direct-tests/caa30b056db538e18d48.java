/*
 * @test
 * @summary Test java.util.Collections.get(int index) method behavior
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {

    static class TestElement<E> {
        private final E element;
        private final int n;

        TestElement(E element, int n) {
            this.element = element;
            this.n = n;
        }

        public E get(int index) {
            if (index < 0 || index >= n)
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
            return element;
        }
    }

    public static void main(String[] args) {
        TestElement<String> te = new TestElement<>("test", 3);

        // Valid indices
        if (!"test".equals(te.get(0))) {
            throw new Error("Expected 'test' at index 0");
        }
        if (!"test".equals(te.get(2))) {
            throw new Error("Expected 'test' at index 2");
        }

        // Invalid indices - expect IndexOutOfBoundsException
        try {
            te.get(-1);
            throw new Error("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            te.get(3);
            throw new Error("Expected IndexOutOfBoundsException for index 3");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
