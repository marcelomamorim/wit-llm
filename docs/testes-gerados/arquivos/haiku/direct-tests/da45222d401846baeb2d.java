/*
 * @test
 * @summary Verifies ListIterator.add behavior of LinkedList through its public API
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAddAtStartOfList();
        testAddInMiddleAfterNext();
        testAddAtEndOfList();
        testAddToEmptyList();
        testAddNullElement();
        testSequentialAdds();
        testAddAfterExternalModificationThrowsCME();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddAtStartOfList() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("b", "c"));
        ListIterator<String> it = list.listIterator();
        it.add("a");
        check(list.equals(Arrays.asList("a", "b", "c")), "add at start: wrong contents");
        check(it.nextIndex() == 1, "add at start: nextIndex should be 1");
        check(it.previousIndex() == 0, "add at start: previousIndex should be 0");
    }

    static void testAddInMiddleAfterNext() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 3));
        ListIterator<Integer> it = list.listIterator();
        check(it.next() == 1, "middle add: first next() should be 1");
        it.add(2);
        check(list.equals(Arrays.asList(1, 2, 3)), "middle add: wrong contents");
        check(it.next() == 3, "middle add: next element after insert should be 3");
    }

    static void testAddAtEndOfList() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a"));
        ListIterator<String> it = list.listIterator(1);
        it.add("b");
        check(list.equals(Arrays.asList("a", "b")), "end add: wrong contents");
        check(it.nextIndex() == 2, "end add: nextIndex should be 2");
        check(!it.hasNext(), "end add: hasNext should be false");
    }

    static void testAddToEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator();
        it.add("x");
        check(list.size() == 1, "empty add: size should be 1");
        check("x".equals(list.get(0)), "empty add: element should be x");
    }

    static void testAddNullElement() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator();
        it.add(null);
        check(list.size() == 1, "null add: size should be 1");
        check(list.contains(null), "null add: list should contain null");
        check(list.get(0) == null, "null add: element should be null");
    }

    static void testSequentialAdds() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator();
        it.add("a");
        it.add("b");
        it.add("c");
        check(list.equals(Arrays.asList("a", "b", "c")), "sequential adds: wrong order");
        check(it.nextIndex() == 3, "sequential adds: nextIndex should be 3");
    }

    static void testAddAfterExternalModificationThrowsCME() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1));
        ListIterator<Integer> it = list.listIterator();
        list.add(2);
        boolean thrown = false;
        try {
            it.add(3);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "add after external modification should throw ConcurrentModificationException");
    }
}
