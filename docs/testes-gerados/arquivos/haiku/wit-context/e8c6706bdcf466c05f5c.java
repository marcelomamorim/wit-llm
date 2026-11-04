/*
 * @test
 * @summary Behavioral tests for the LinkedList(Collection) constructor
 * @run main LinkedListWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testCopiesElementsInOrder();
        testEmptySource();
        testNullSourceThrowsNPE();
        testNullElementsPreserved();
        testCopyIsIndependentOfSource();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testCopiesElementsInOrder() {
        List<Integer> src = Arrays.asList(1, 2, 3);
        LinkedList<Integer> list = new LinkedList<>(src);
        check(list.size() == 3, "size should be 3");
        check(list.equals(Arrays.asList(1, 2, 3)), "elements should keep order");
        check(list.getFirst().equals(1), "first element mismatch");
        check(list.getLast().equals(3), "last element mismatch");
    }

    static void testEmptySource() {
        LinkedList<String> list = new LinkedList<String>(Collections.<String>emptyList());
        check(list.isEmpty(), "list built from empty collection should be empty");
        check(list.size() == 0, "size of empty copy should be 0");
    }

    static void testNullSourceThrowsNPE() {
        boolean thrown = false;
        Collection<String> nullSource = null;
        try {
            new LinkedList<String>(nullSource);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null source collection should throw NullPointerException");
    }

    static void testNullElementsPreserved() {
        List<String> src = Arrays.asList("a", null, "c");
        LinkedList<String> list = new LinkedList<>(src);
        check(list.size() == 3, "size should include null elements");
        check(list.get(1) == null, "null element should be preserved");
        check(list.get(2).equals("c"), "element after null mismatch");
    }

    static void testCopyIsIndependentOfSource() {
        List<Integer> src = new ArrayList<>();
        src.add(10);
        src.add(20);
        LinkedList<Integer> list = new LinkedList<>(src);
        src.add(30);
        src.set(0, 99);
        check(list.size() == 2, "copy should not reflect later source additions");
        check(list.getFirst().equals(10), "copy should not reflect later source updates");
        list.add(40);
        check(src.size() == 3, "modifying copy should not affect source size");
        check(src.get(0).equals(99), "source should keep its own state");
    }
}
