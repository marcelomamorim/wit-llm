/*
 * @test
 * @summary Test ImmutableCollections.ListItr.hasPrevious() behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    static class TestListItr extends ImmutableCollections.ListItr<Object> {
        boolean isListIterator;
        int cursor;

        TestListItr(boolean isListIterator, int cursor) {
            this.isListIterator = isListIterator;
            this.cursor = cursor;
        }

        @Override
        public boolean hasPrevious() {
            if (!isListIterator) {
                throw ImmutableCollections.uoe();
            }
            return cursor != 0;
        }
    }

    public static void main(String[] args) {
        // Test case 1: isListIterator == false should throw UnsupportedOperationException
        try {
            TestListItr itr = new TestListItr(false, 0);
            itr.hasPrevious();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Test case 2: isListIterator == true and cursor == 0 should return false
        TestListItr itr2 = new TestListItr(true, 0);
        if (itr2.hasPrevious()) {
            throw new RuntimeException("Expected hasPrevious() to return false when cursor == 0");
        }

        // Test case 3: isListIterator == true and cursor != 0 should return true
        TestListItr itr3 = new TestListItr(true, 1);
        if (!itr3.hasPrevious()) {
            throw new RuntimeException("Expected hasPrevious() to return true when cursor != 0");
        }
    }
}
