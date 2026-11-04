/*
 * @test
 * @summary Witup-style generated tests for LinkedList.remove(int index)
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.List;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        removeFirstReturnsFirstElement();
        removeLastReturnsLastElement();
        removeMiddleReturnsElementAndShrinksList();
        removeNullElementByIndexReturnsNull();
        removeFromSingleElementListLeavesEmptyList();
        removeNegativeIndexThrows();
        removeIndexEqualToSizeThrows();
        removeFromEmptyListThrows();
        removeIndexOutOfRangeDoesNotModifyList();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static LinkedList<String> abc() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }

    static void removeFirstReturnsFirstElement() {
        LinkedList<String> list = abc();
        String removed = list.remove(0);
        check("a".equals(removed), "expected a but was " + removed);
        check(list.size() == 2, "expected size 2");
        check("b".equals(list.get(0)), "expected b at index 0");
        check("c".equals(list.get(1)), "expected c at index 1");
    }

    static void removeLastReturnsLastElement() {
        LinkedList<String> list = abc();
        String removed = list.remove(2);
        check("c".equals(removed), "expected c but was " + removed);
        check(list.size() == 2, "expected size 2");
        check("a".equals(list.get(0)), "expected a at index 0");
        check("b".equals(list.get(1)), "expected b at index 1");
    }

    static void removeMiddleReturnsElementAndShrinksList() {
        LinkedList<String> list = abc();
        String removed = list.remove(1);
        check("b".equals(removed), "expected b but was " + removed);
        check(list.size() == 2, "expected size 2");
        check(list.indexOf("b") == -1, "b should no longer be present");
        check("a".equals(list.get(0)) && "c".equals(list.get(1)), "unexpected order");
    }

    static void removeNullElementByIndexReturnsNull() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("x");
        String removed = list.remove(0);
        check(removed == null, "expected null");
        check(list.size() == 1, "expected size 1");
        check("x".equals(list.get(0)), "expected x remaining");
    }

    static void removeFromSingleElementListLeavesEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("only");
        String removed = list.remove(0);
        check("only".equals(removed), "expected only");
        check(list.isEmpty(), "expected empty list");
    }

    static void removeNegativeIndexThrows() {
        LinkedList<String> list = abc();
        boolean thrown = false;
        try {
            list.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected IndexOutOfBoundsException for -1");
        check(list.size() == 3, "list must be unchanged");
    }

    static void removeIndexEqualToSizeThrows() {
        LinkedList<String> list = abc();
        boolean thrown = false;
        try {
            list.remove(3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected IndexOutOfBoundsException for index == size");
        check(list.size() == 3, "list must be unchanged");
    }

    static void removeFromEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.remove(0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected IndexOutOfBoundsException on empty list");
        check(list.isEmpty(), "list must remain empty");
    }

    static void removeIndexOutOfRangeDoesNotModifyList() {
        List<String> list = abc();
        try {
            list.remove(10);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        check(list.size() == 3, "size must remain 3");
        check("a".equals(list.get(0)) && "b".equals(list.get(1)) && "c".equals(list.get(2)),
              "contents must remain unchanged");
    }
}
