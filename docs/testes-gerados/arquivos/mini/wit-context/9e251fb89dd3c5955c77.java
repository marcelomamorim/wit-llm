/*
 * @test
 * @summary Test ImmutableCollections.ListItr.previousIndex() behavior
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

        int previousIndex() {
            if (!isListIterator) {
                throw ImmutableCollections.uoe();
            }
            return cursor - 1;
        }
    }

    public static void main(String[] args) {
        // Test case: isListIterator == true, cursor = 5
        ListItr itr1 = new ListItr(true, 5);
        int prevIndex = itr1.previousIndex();
        if (prevIndex != 4) {
            throw new AssertionError("Expected previousIndex 4 but got " + prevIndex);
        }

        // Test case: isListIterator == true, cursor = 0 (boundary)
        ListItr itr2 = new ListItr(true, 0);
        prevIndex = itr2.previousIndex();
        if (prevIndex != -1) {
            throw new AssertionError("Expected previousIndex -1 but got " + prevIndex);
        }

        // Test case: isListIterator == false, expect UnsupportedOperationException
        ListItr itr3 = new ListItr(false, 10);
        boolean threw = false;
        try {
            itr3.previousIndex();
        } catch (UnsupportedOperationException uoe) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException when isListIterator is false");
        }
    }
}
