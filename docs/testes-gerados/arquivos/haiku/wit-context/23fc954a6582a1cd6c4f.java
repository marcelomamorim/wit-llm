/*
 * @test
 * @summary Verify ArrayList.remove(int) index validation and observable removal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveOutOfBoundsThrows(int index) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.remove(index);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        check(list.size() == 2, "List size must be unchanged after failed remove");
        check("a".equals(list.get(0)) && "b".equals(list.get(1)),
              "List contents must be unchanged after failed remove");
    }

    static void testRemoveOnEmptyListThrows() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.remove(0);
            throw new RuntimeException("Expected IndexOutOfBoundsException on empty list");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        check(list.isEmpty(), "Empty list must remain empty");
    }

    static void testRemoveValidIndexReturnsElementAndShifts() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        String removed = list.remove(1);
        check("y".equals(removed), "remove(1) must return the element at index 1");
        check(list.size() == 2, "Size must decrease by one");
        check("x".equals(list.get(0)) && "z".equals(list.get(1)),
              "Remaining elements must shift left");
    }

    static void testRemoveFirstAndLastBoundaries() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        check(list.remove(2).intValue() == 30, "remove(size-1) must return last element");
        check(list.remove(0).intValue() == 10, "remove(0) must return first element");
        check(list.size() == 1 && list.get(0) == 20, "Only element 20 must remain");
    }

    static void testRemoveNullElement() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("n");
        String removed = ((ArrayList<String>) list).remove(0);
        check(removed == null, "remove(0) must return null element");
        check(list.size() == 1 && "n".equals(list.get(0)), "Remaining element must be preserved");
    }

    public static void main(String[] args) {
        testRemoveOutOfBoundsThrows(-1);
        testRemoveOutOfBoundsThrows(2);
        testRemoveOutOfBoundsThrows(Integer.MAX_VALUE);
        testRemoveOnEmptyListThrows();
        testRemoveValidIndexReturnsElementAndShifts();
        testRemoveFirstAndLastBoundaries();
        testRemoveNullElement();
    }
}
