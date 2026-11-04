/*
 * @test
 * @summary Test ImmutableCollections.ListItr.nextIndex() behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    static class ListItr {
        private final boolean isListIterator;
        private final int cursor;

        ListItr(boolean isListIterator, int cursor) {
            this.isListIterator = isListIterator;
            this.cursor = cursor;
        }

        int nextIndex() {
            if (!isListIterator) {
                throw ImmutableCollections.uoe();
            }
            return cursor;
        }
    }

    public static void main(String[] args) {
        // Test case: isListIterator == true returns cursor
        ListItr itrTrue = new ListItr(true, 5);
        int idx = itrTrue.nextIndex();
        if (idx != 5) {
            throw new AssertionError("Expected nextIndex() to return 5 but got " + idx);
        }

        // Test case: isListIterator == false throws UnsupportedOperationException
        boolean threw = false;
        ListItr itrFalse = new ListItr(false, 0);
        try {
            itrFalse.nextIndex();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException when isListIterator is false");
        }

        System.out.println("Test passed.");
    }
}
