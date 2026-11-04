/*
 * @test
 * @summary Verifies that AbstractCollection.add(E) throws UnsupportedOperationException by default and that overriding it enables adding elements
 * @run main AbstractCollectionWitupTest
 */

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstractCollectionWitupTest {

    static class EmptyCollection extends AbstractCollection<String> {
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                public boolean hasNext() {
                    return false;
                }
                public String next() {
                    throw new NoSuchElementException();
                }
            };
        }

        public int size() {
            return 0;
        }
    }

    static class AppendableCollection extends AbstractCollection<String> {
        private final ArrayList<String> list = new ArrayList<>();

        public Iterator<String> iterator() {
            return list.iterator();
        }

        public int size() {
            return list.size();
        }

        public boolean add(String e) {
            return list.add(e);
        }
    }

    public static void main(String[] args) {
        testDefaultAddThrowsUnsupportedOperation();
        testDefaultAddNullThrowsUnsupportedOperation();
        testOverriddenAddStoresElement();
    }

    static void testDefaultAddThrowsUnsupportedOperation() {
        EmptyCollection c = new EmptyCollection();
        boolean thrown = false;
        try {
            c.add("x");
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from add(E)");
        }
        if (c.size() != 0) {
            throw new RuntimeException("Size changed after failed add: " + c.size());
        }
    }

    static void testDefaultAddNullThrowsUnsupportedOperation() {
        EmptyCollection c = new EmptyCollection();
        boolean thrown = false;
        try {
            c.add(null);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from add(null)");
        }
    }

    static void testOverriddenAddStoresElement() {
        AppendableCollection c = new AppendableCollection();
        if (!c.add("x")) {
            throw new RuntimeException("add should return true");
        }
        if (c.size() != 1) {
            throw new RuntimeException("Unexpected size: " + c.size());
        }
        if (!c.contains("x")) {
            throw new RuntimeException("contains should find added element");
        }
    }
}
