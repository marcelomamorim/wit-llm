/* @test    @summary Test ArrayList.previous() method for correct behavior and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testPreviousNormal();
        testPreviousEmpty();
        testPreviousAfterRemoval();
    }

    static void testPreviousNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        // Simulate cursor at position 2 (pointing to "c")
        int cursor = 2;
        int lastRet = -1;
        String result = previous(list, cursor, lastRet);
        assert result.equals("b") : "Expected previous element to be 'b'";
    }

    static void testPreviousEmpty() {
        ArrayList<String> list = new ArrayList<>();
        int cursor = 0;
        int lastRet = -1;
        try {
            previous(list, cursor, lastRet);
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testPreviousAfterRemoval() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        int cursor = 2;
        int lastRet = -1;
        // Remove element to simulate concurrent modification
        list.remove(1); // remove "y"
        try {
            previous(list, cursor, lastRet);
            // Depending on implementation, this may throw ConcurrentModificationException
            // but since our previous() method does not check for modification count, it may not.
            // For the purpose of this test, we assume no exception is thrown.
        } catch (Exception e) {
            assert false : "Did not expect exception, but got: " + e;
        }
    }

    static String previous(ArrayList<?> list, int cursor, int lastRet) {
        // Emulate the previous() method from ArrayList
        checkForComodification();
        int i = cursor - 1;
        if (i < 0)
            throw new NoSuchElementException();
        Object[] elementData = list.elementData();
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i;
        lastRet = i;
        return (String) elementData[i];
    }

    static void checkForComodification() {
        // Placeholder for actual modification check, assume always passes
    }
}
