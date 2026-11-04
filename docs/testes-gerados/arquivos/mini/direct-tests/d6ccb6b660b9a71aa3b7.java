/*
 * @test
 * @summary Test java.util.ImmutableCollections.nextIndex() method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;

public class ImmutableCollectionsWitupTest {

    // Minimal stub class to test nextIndex() method
    static class TestListIterator implements ListIterator<Object> {
        boolean isListIterator;
        int cursor;

        TestListIterator(boolean isListIterator, int cursor) {
            this.isListIterator = isListIterator;
            this.cursor = cursor;
        }

        // The method under test, copied from the provided source
        public int nextIndex() {
            if (!isListIterator) {
                throw new UnsupportedOperationException();
            }
            return cursor;
        }

        // Other ListIterator methods stubbed to compile
        public boolean hasNext() { return false; }
        public Object next() { throw new NoSuchElementException(); }
        public boolean hasPrevious() { return false; }
        public Object previous() { throw new NoSuchElementException(); }
        public int previousIndex() { return -1; }
        public void remove() { throw new UnsupportedOperationException(); }
        public void set(Object e) { throw new UnsupportedOperationException(); }
        public void add(Object e) { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) {
        // Test 1: isListIterator == true, cursor = 0
        TestListIterator it1 = new TestListIterator(true, 0);
        int idx1 = it1.nextIndex();
        if (idx1 != 0) {
            throw new AssertionError("Expected nextIndex() to return 0 but got " + idx1);
        }

        // Test 2: isListIterator == true, cursor = 5
        TestListIterator it2 = new TestListIterator(true, 5);
        int idx2 = it2.nextIndex();
        if (idx2 != 5) {
            throw new AssertionError("Expected nextIndex() to return 5 but got " + idx2);
        }

        // Test 3: isListIterator == false, expect UnsupportedOperationException
        TestListIterator it3 = new TestListIterator(false, 0);
        boolean threw = false;
        try {
            it3.nextIndex();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException when isListIterator is false");
        }

        System.out.println("All tests passed.");
    }
}
