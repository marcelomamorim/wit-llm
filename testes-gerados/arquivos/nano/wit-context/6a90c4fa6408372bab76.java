/* @test    @summary Test for LinkedList.listIterator(int) method, especially for IndexOutOfBoundsException.    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testListIteratorWithValidIndex();
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexGreaterThanSize();
    }

    private static void testListIteratorWithValidIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int index = 1;
        try {
            ListIterator<Integer> it = list.listIterator(index);
            if (it.next() != 2) {
                throw new AssertionError("Expected first element to be 2 at index 1");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException for valid index", e);
        }
    }

    private static void testListIteratorWithNegativeIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int index = -1;
        try {
            list.listIterator(index);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testListIteratorWithIndexGreaterThanSize() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int index = list.size() + 1;
        try {
            list.listIterator(index);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
