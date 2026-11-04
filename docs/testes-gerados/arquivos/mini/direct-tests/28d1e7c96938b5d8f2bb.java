/*
 * @test
 * @summary Test ImmutableCollections.hasPrevious() method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    static class TestIterator implements ListIterator<Object> {
        boolean isListIterator;
        int cursor;

        TestIterator(boolean isListIterator, int cursor) {
            this.isListIterator = isListIterator;
            this.cursor = cursor;
        }

        private UnsupportedOperationException uoe() {
            return new UnsupportedOperationException();
        }

        public boolean hasPrevious() {
            if (!isListIterator) {
                throw uoe();
            }
            return cursor != 0;
        }

        // Dummy implementations to satisfy interface
        public boolean hasNext() { return false; }
        public Object next() { throw new NoSuchElementException(); }
        public int nextIndex() { return 0; }
        public Object previous() { throw new NoSuchElementException(); }
        public int previousIndex() { return -1; }
        public void remove() { throw uoe(); }
        public void set(Object e) { throw uoe(); }
        public void add(Object e) { throw uoe(); }
    }

    public static void main(String[] args) {
        // Case 1: isListIterator == false -> should throw UnsupportedOperationException
        TestIterator it1 = new TestIterator(false, 0);
        try {
            it1.hasPrevious();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Case 2: isListIterator == true, cursor == 0 -> hasPrevious() returns false
        TestIterator it2 = new TestIterator(true, 0);
        if (it2.hasPrevious()) {
            throw new RuntimeException("Expected hasPrevious() to return false when cursor == 0");
        }

        // Case 3: isListIterator == true, cursor > 0 -> hasPrevious() returns true
        TestIterator it3 = new TestIterator(true, 1);
        if (!it3.hasPrevious()) {
            throw new RuntimeException("Expected hasPrevious() to return true when cursor > 0");
        }

        System.out.println("PASS");
    }
}
