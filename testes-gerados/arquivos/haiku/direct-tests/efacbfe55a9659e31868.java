/*
 * @test
 * @summary Verifies ArrayList.listIterator(int) positioning, bounds checking and traversal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testValidIndexes();
        testOutOfRangeIndexes();
        testEmptyListIterator();
        testTraversalFromIndex();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ArrayList<String> threeElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }

    static void testValidIndexes() {
        ArrayList<String> list = threeElements();
        for (int i = 0; i <= list.size(); i++) {
            ListIterator<String> it = list.listIterator(i);
            check(it.nextIndex() == i, "nextIndex mismatch at " + i);
            check(it.previousIndex() == i - 1, "previousIndex mismatch at " + i);
            check(it.hasPrevious() == (i > 0), "hasPrevious mismatch at " + i);
            check(it.hasNext() == (i < list.size()), "hasNext mismatch at " + i);
        }
    }

    static void expectIndexOutOfBounds(ArrayList<String> list, int index) {
        boolean thrown = false;
        try {
            list.listIterator(index);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected IndexOutOfBoundsException for index " + index);
    }

    static void testOutOfRangeIndexes() {
        ArrayList<String> list = threeElements();
        expectIndexOutOfBounds(list, list.size() + 1);
        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(new ArrayList<String>(), 1);
    }

    static void testEmptyListIterator() {
        ArrayList<String> list = new ArrayList<>();
        ListIterator<String> it = list.listIterator(0);
        check(!it.hasNext(), "empty list iterator should have no next");
        check(!it.hasPrevious(), "empty list iterator should have no previous");
        check(it.nextIndex() == 0, "empty list nextIndex should be 0");
    }

    static void testTraversalFromIndex() {
        ArrayList<String> list = threeElements();
        ListIterator<String> it = list.listIterator(1);
        check("b".equals(it.next()), "next from index 1 should be b");
        check("b".equals(it.previous()), "previous after next should be b");
        ListIterator<String> end = list.listIterator(list.size());
        check("c".equals(end.previous()), "previous from end should be c");
        check(!end.hasNext(), "iterator at end should have no next");
    }
}
