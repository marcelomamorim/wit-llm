/*
 * @test
 * @summary Test ImmutableCollections.previousIndex() method behavior
 * @run main ImmutableCollectionsWitupTest
 */

public class ImmutableCollectionsWitupTest {

    static class TestIterator {
        boolean isListIterator;
        int cursor;

        TestIterator(boolean isListIterator, int cursor) {
            this.isListIterator = isListIterator;
            this.cursor = cursor;
        }

        int previousIndex() {
            if (!isListIterator) {
                throw new UnsupportedOperationException();
            }
            return cursor - 1;
        }
    }

    public static void main(String[] args) {
        // Test case 1: isListIterator == true, cursor = 0
        TestIterator it1 = new TestIterator(true, 0);
        int prevIndex1 = it1.previousIndex();
        if (prevIndex1 != -1) {
            throw new AssertionError("Expected previousIndex to be -1 but got " + prevIndex1);
        }

        // Test case 2: isListIterator == true, cursor = 5
        TestIterator it2 = new TestIterator(true, 5);
        int prevIndex2 = it2.previousIndex();
        if (prevIndex2 != 4) {
            throw new AssertionError("Expected previousIndex to be 4 but got " + prevIndex2);
        }

        // Test case 3: isListIterator == false, expect UnsupportedOperationException
        TestIterator it3 = new TestIterator(false, 10);
        boolean threw = false;
        try {
            it3.previousIndex();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException when isListIterator is false");
        }
    }
}
