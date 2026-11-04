/*
 * @test
 * @summary Verifies ArrayList.ListItr.add(E) observable behavior through the public ListIterator API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddInsertsAtCursor();
        testAddAdvancesCursorSoNextReturnsFollowingElement();
        testAddResetsLastRetSoRemoveThrowsIllegalState();
        testAddNullElement();
        testAddConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddInsertsAtCursor() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(1);
        it.add("x");
        check(list.size() == 4, "size should be 4 after add");
        check(list.get(0).equals("a"), "index 0 should be a");
        check(list.get(1).equals("x"), "element should be inserted at cursor");
        check(list.get(2).equals("b"), "b should shift right");
        check(list.get(3).equals("c"), "c should shift right");
    }

    static void testAddAdvancesCursorSoNextReturnsFollowingElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(1);
        it.add("x");
        check(it.hasNext(), "iterator should have next element");
        check(it.next().equals("b"), "next after add should return b");
        it.add("y");
        check(list.size() == 5, "size should be 5 after second add");
        check(list.get(3).equals("y"), "second add should insert before c");
        check(list.get(4).equals("c"), "c should remain last");
    }

    static void testAddResetsLastRetSoRemoveThrowsIllegalState() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.add("x");
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "remove after add should throw IllegalStateException");
        check(list.size() == 3, "list size should be unchanged by failed remove");
    }

    static void testAddNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator(1);
        it.add(null);
        check(list.size() == 2, "size should be 2 after adding null");
        check(list.get(1) == null, "null element should be stored");
    }

    static void testAddConcurrentModificationDetected() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        list.add("z");
        boolean threw = false;
        try {
            it.add("y");
        } catch (ConcurrentModificationException e) {
            threw = true;
        }
        check(threw, "iterator add after external structural change should throw CME");
    }
}
