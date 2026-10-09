/* @test    @summary Test getFirst() method of LinkedList for normal and empty list scenarios    @run main LinkedListWitupTest_P7e04b2b549a7 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P7e04b2b549a7 {

    public static void main(String[] args) {
        testGetFirstWithElements();
        testGetFirstEmptyList();
    }

    private static void testGetFirstWithElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        Integer first = list.getFirst();
        if (first == null || first != 10) {
            throw new AssertionError("Expected first element to be 10, but got: " + first);
        }
    }

    private static void testGetFirstEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException for empty list");
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }
}
