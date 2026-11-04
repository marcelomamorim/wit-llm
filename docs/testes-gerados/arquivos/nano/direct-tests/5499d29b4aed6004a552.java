/* @test    @summary Test previous() method of ImmutableCollections class    @run main ImmutableCollectionsWitupTest */

import java.util.NoSuchElementException;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPreviousWithValidIndex();
        testPreviousWithInvalidIndex();
    }

    static void testPreviousWithValidIndex() {
        class DummyList {
            int getCount = 0;
            Object get(int index) {
                getCount++;
                return "element" + index;
            }
        }
        class DummyListIterator extends ImmutableCollections {
            int cursor = 1;
            boolean isListIterator = true;
            DummyList list = new DummyList();
            public Object previous() {
                if (!isListIterator) {
                    throw new UnsupportedOperationException();
                }
                try {
                    int i = cursor - 1;
                    Object previous = list.get(i);
                    cursor = i;
                    return previous;
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }
        }
        DummyListIterator iterator = new DummyListIterator();
        Object result = iterator.previous();
        if (!"element0".equals(result)) {
            throw new AssertionError("Expected 'element0', got: " + result);
        }
        if (iterator.cursor != 0) {
            throw new AssertionError("Cursor not updated correctly.");
        }
        if (iterator.list.getCount != 1) {
            throw new AssertionError("List.get() called more than once.");
        }
    }

    static void testPreviousWithInvalidIndex() {
        class DummyList {
            Object get(int index) {
                throw new IndexOutOfBoundsException();
            }
        }
        class DummyListIterator extends ImmutableCollections {
            int cursor = 0;
            boolean isListIterator = true;
            DummyList list = new DummyList();
            public Object previous() {
                if (!isListIterator) {
                    throw new UnsupportedOperationException();
                }
                try {
                    int i = cursor - 1;
                    Object previous = list.get(i);
                    cursor = i;
                    return previous;
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }
        }
        DummyListIterator iterator = new DummyListIterator();
        boolean threw = false;
        try {
            iterator.previous();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException.");
        }
    }
}
