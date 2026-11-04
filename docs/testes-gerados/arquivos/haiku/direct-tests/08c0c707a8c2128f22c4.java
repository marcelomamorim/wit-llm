/* 
 * @test
 * @summary Verifies LinkedList.add(int, E) positional insertion, null handling and bounds checking
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAppendAtSize();
        testInsertAtFront();
        testInsertInMiddle();
        testInsertNullElement();
        testOutOfBoundsThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testAppendAtSize() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "a");
        check(list.equals(Arrays.asList("a")), "add(0) on empty list should append");
        list.add(1, "b");
        check(list.equals(Arrays.asList("a", "b")), "add(size) should append at tail");
        check(list.size() == 2, "size should be 2");
    }

    static void testInsertAtFront() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("b", "c"));
        list.add(0, "a");
        check(list.equals(Arrays.asList("a", "b", "c")), "add(0) should insert at head");
        check("a".equals(list.getFirst()), "first element should be inserted element");
    }

    static void testInsertInMiddle() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "c", "d"));
        list.add(1, "b");
        check(list.equals(Arrays.asList("a", "b", "c", "d")), "middle insertion order incorrect");
        check("b".equals(list.get(1)), "get(1) should return inserted element");
        check("c".equals(list.get(2)), "shifted element should move right");
        check(list.size() == 4, "size should be 4 after insertion");
    }

    static void testInsertNullElement() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("x"));
        list.add(1, null);
        list.add(0, null);
        check(list.size() == 3, "null elements should be accepted");
        check(list.get(0) == null, "head null should be stored");
        check(list.get(2) == null, "tail null should be stored");
        check("x".equals(list.get(1)), "non-null element should be preserved");
    }

    static void testOutOfBoundsThrows() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b"));
        try {
            list.add(-1, "x");
            throw new AssertionError("add(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        try {
            list.add(3, "x");
            throw new AssertionError("add(size+1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        check(list.equals(Arrays.asList("a", "b")), "list must be unchanged after failed add");
    }
}
