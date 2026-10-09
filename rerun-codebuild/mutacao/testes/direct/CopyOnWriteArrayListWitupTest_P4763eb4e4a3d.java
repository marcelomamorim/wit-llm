/*
 * @test    @summary Test CopyOnWriteArrayList.listIterator(int) for boundary conditions and correctness
 * @run main CopyOnWriteArrayListWitupTest_P4763eb4e4a3d
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;

public class CopyOnWriteArrayListWitupTest_P4763eb4e4a3d {

    public static void main(String[] args) {
        testListIteratorAtStart();
        testListIteratorAtEnd();
        testListIteratorInvalidNegative();
        testListIteratorInvalidTooLarge();
        testListIteratorCorrectTraversal();
    }

    static void testListIteratorAtStart() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(0);
        assert it.hasNext() : "Iterator at start should have next";
        assert it.next().equals("a") : "First element should be 'a'";
        assert it.next().equals("b") : "Second element should be 'b'";
        assert it.next().equals("c") : "Third element should be 'c'";
        assert !it.hasNext() : "Iterator at end should not have next";
    }

    static void testListIteratorAtEnd() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        ListIterator<String> it = list.listIterator(list.size());
        assert !it.hasNext() : "Iterator at end should not have next";
        assert it.hasPrevious() : "Iterator at end should have previous";
        assert it.previous().equals("z") : "Previous element should be 'z'";
        assert it.previous().equals("y") : "Previous element should be 'y'";
        assert it.previous().equals("x") : "Previous element should be 'x'";
        assert !it.hasPrevious() : "Iterator at start should not have previous";
    }

    static void testListIteratorInvalidNegative() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        try {
            list.listIterator(-1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testListIteratorInvalidTooLarge() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        try {
            list.listIterator(2); // list size is 1
            assert false : "Expected IndexOutOfBoundsException for index > size";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testListIteratorCorrectTraversal() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        ListIterator<Integer> it = list.listIterator(2); // start at index 2
        assert it.hasNext() : "Iterator should have next";
        assert it.next() == 3 : "Next element should be 3";
        assert it.next() == 4 : "Next element should be 4";
        assert it.previous() == 4 : "Previous element should be 4";
        assert it.previous() == 3 : "Previous element should be 3";
        assert it.next() == 3 : "Next element again should be 3";
    }
}
