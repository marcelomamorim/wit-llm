/*
 * @test
 * @summary Behavioral tests for LinkedList.listIterator(int index): position, navigation, bounds checking and add through the returned iterator
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyListAtZero();
        testMiddleIndex();
        testEndIndex();
        testInvalidIndexes();
        testAddThroughIterator();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyListAtZero() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator(0);
        check(!it.hasNext(), "empty: hasNext should be false");
        check(!it.hasPrevious(), "empty: hasPrevious should be false");
        check(it.nextIndex() == 0, "empty: nextIndex should be 0");
        check(it.previousIndex() == -1, "empty: previousIndex should be -1");
    }

    static void testMiddleIndex() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c"));
        ListIterator<String> it = list.listIterator(1);
        check(it.nextIndex() == 1, "middle: initial nextIndex should be 1");
        check(it.hasNext(), "middle: hasNext should be true");
        check(it.hasPrevious(), "middle: hasPrevious should be true");
        check("b".equals(it.next()), "middle: next() should return b");
        check(it.nextIndex() == 2, "middle: nextIndex after next should be 2");
        check("b".equals(it.previous()), "middle: previous() should return b");
        check(it.nextIndex() == 1, "middle: nextIndex after previous should be 1");
        check(it.previousIndex() == 0, "middle: previousIndex should be 0");
    }

    static void testEndIndex() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c"));
        ListIterator<String> it = list.listIterator(3);
        check(!it.hasNext(), "end: hasNext should be false");
        check(it.hasPrevious(), "end: hasPrevious should be true");
        check(it.nextIndex() == 3, "end: nextIndex should be 3");
        check("c".equals(it.previous()), "end: previous() should return c");
    }

    static void testInvalidIndexes() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c"));
        int[] invalid = {-1, 4};
        for (int index : invalid) {
            boolean thrown = false;
            try {
                list.listIterator(index);
            } catch (IndexOutOfBoundsException e) {
                thrown = true;
            }
            check(thrown, "index " + index + " on size 3 should throw IndexOutOfBoundsException");
        }

        LinkedList<String> empty = new LinkedList<>();
        boolean thrownEmpty = false;
        try {
            empty.listIterator(1);
        } catch (IndexOutOfBoundsException e) {
            thrownEmpty = true;
        }
        check(thrownEmpty, "index 1 on empty list should throw IndexOutOfBoundsException");
    }

    static void testAddThroughIterator() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "c"));
        ListIterator<String> it = list.listIterator(1);
        it.add("b");
        check(list.toString().equals("[a, b, c]"), "add via iterator should insert at index 1, got " + list);
        check(it.nextIndex() == 2, "nextIndex after add should be 2");
        check("c".equals(it.next()), "next() after add should return c");
    }
}
