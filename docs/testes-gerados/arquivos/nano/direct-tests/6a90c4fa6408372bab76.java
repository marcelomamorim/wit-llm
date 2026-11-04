/* @test    @summary Test LinkedList.listIterator(int index) with valid and invalid indices    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testListIteratorValidIndex();
        testListIteratorNegativeIndex();
        testListIteratorIndexBeyondSize();
    }

    private static void testListIteratorValidIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        int index = 1;
        ListIterator<Integer> iterator = list.listIterator(index);
        if (iterator.next() != 20) {
            throw new AssertionError("Expected first element to be 20 at index 1");
        }
    }

    private static void testListIteratorNegativeIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testListIteratorIndexBeyondSize() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(5);
        list.add(6);
        try {
            list.listIterator(3);
            throw new AssertionError("Expected IndexOutOfBoundsException for index beyond size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
