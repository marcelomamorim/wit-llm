/*
 * @test
 * @summary Verifies LinkedList.remove() returns the head element and throws NoSuchElementException when the list is empty
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        removeOnEmptyListThrows();
        removeReturnsHeadAndShrinksList();
        removeOnDrainedListThrows();
        removeReturnsNullHeadElement();
    }

    static void removeOnEmptyListThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.remove();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("remove() on empty list must throw NoSuchElementException");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("list must remain empty after failed remove()");
        }
    }

    static void removeReturnsHeadAndShrinksList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int head = list.remove();
        if (head != 1) {
            throw new RuntimeException("remove() must return head element, got " + head);
        }
        if (list.size() != 2) {
            throw new RuntimeException("size after remove() must be 2, got " + list.size());
        }
        if (list.getFirst() != 2) {
            throw new RuntimeException("new head must be 2, got " + list.getFirst());
        }
    }

    static void removeOnDrainedListThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.remove();
        boolean thrown = false;
        try {
            list.remove();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("remove() on drained list must throw NoSuchElementException");
        }
    }

    static void removeReturnsNullHeadElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(null);
        list.add(7);
        Integer head = list.remove();
        if (head != null) {
            throw new RuntimeException("remove() must return null head element");
        }
        if (list.size() != 1 || list.getFirst() != 7) {
            throw new RuntimeException("list must contain only 7 after removing null head");
        }
    }
}
