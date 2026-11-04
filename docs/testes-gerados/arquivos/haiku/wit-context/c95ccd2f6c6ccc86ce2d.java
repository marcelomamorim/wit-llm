/*
 * @test
 * @summary Verifies that AbstractCollection.add(E) throws UnsupportedOperationException by default for subclasses that do not override it
 * @run main AbstractCollectionWitupTest
 */

import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Iterator;

public class AbstractCollectionWitupTest {

    static class EmptyCollection extends AbstractCollection<String> {
        @Override
        public Iterator<String> iterator() {
            return Collections.<String>emptyIterator();
        }

        @Override
        public int size() {
            return 0;
        }
    }

    static class SingletonCollection extends AbstractCollection<String> {
        private final String element = "a";

        @Override
        public Iterator<String> iterator() {
            return Collections.singletonList(element).iterator();
        }

        @Override
        public int size() {
            return 1;
        }
    }

    static void checkAddThrows(AbstractCollection<String> c, String value) {
        boolean thrown = false;
        try {
            c.add(value);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("add(" + value + ") did not throw UnsupportedOperationException");
        }
    }

    public static void main(String[] args) {
        checkAddThrows(new EmptyCollection(), "x");
        checkAddThrows(new EmptyCollection(), null);
        checkAddThrows(new SingletonCollection(), "b");
        checkAddThrows(new SingletonCollection(), null);
    }
}
