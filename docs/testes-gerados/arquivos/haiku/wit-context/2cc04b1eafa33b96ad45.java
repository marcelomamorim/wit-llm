/*
 * @test
 * @summary Witup-derived behavioral checks for ArrayList.remove(int) and its SubList counterpart
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveReturnsElementAndShifts() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        String removed = list.remove(1);
        check("b".equals(removed), "remove(1) should return b");
        check(list.size() == 2, "size should be 2 after remove");
        check("a".equals(list.get(0)), "index 0 should remain a");
        check("c".equals(list.get(1)), "element c should shift to index 1");
    }

    static void testRemoveNullElement() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(null, "x"));
        String removed = list.remove(0);
        check(removed == null, "remove(0) should return null element");
        check(list.size() == 1 && "x".equals(list.get(0)), "remaining list should be [x]");
    }

    static void testRemoveOutOfBoundsThrows() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean thrownHigh = false;
        try {
            list.remove(3);
        } catch (IndexOutOfBoundsException e) {
            thrownHigh = true;
        }
        check(thrownHigh, "remove(size) should throw IndexOutOfBoundsException");
        check(list.size() == 3, "list must be unchanged after failed remove(size)");

        boolean thrownNegative = false;
        try {
            list.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            thrownNegative = true;
        }
        check(thrownNegative, "remove(-1) should throw IndexOutOfBoundsException");
        check(list.size() == 3, "list must be unchanged after failed remove(-1)");
    }

    static void testRemoveOnEmptyListThrows() {
        ArrayList<String> list = new ArrayList<>();
        boolean thrown = false;
        try {
            list.remove(0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "remove(0) on empty list should throw IndexOutOfBoundsException");
    }

    static void testSubListRemoveReturnsAndUpdatesRoot() {
        ArrayList<String> root = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> sub = root.subList(0, 2);
        String removed = sub.remove(0);
        check("a".equals(removed), "subList.remove(0) should return a");
        check(sub.size() == 1, "subList size should drop to 1");
        check(root.size() == 2, "root size should drop to 2");
        check("b".equals(root.get(0)) && "c".equals(root.get(1)), "root should be [b, c]");
    }

    static void testSubListRemoveOutOfBoundsThrows() {
        ArrayList<String> root = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> sub = root.subList(0, 1);
        boolean thrown = false;
        try {
            sub.remove(1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "subList.remove(size) should throw IndexOutOfBoundsException");
        check(root.size() == 3, "root must be unchanged after failed subList remove");
    }

    static void testSubListRemoveAfterStructuralChangeThrowsCME() {
        ArrayList<String> root = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> sub = root.subList(0, 2);
        root.add("d");
        boolean thrown = false;
        try {
            sub.remove(0);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "subList.remove after root structural change should throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testRemoveReturnsElementAndShifts();
        testRemoveNullElement();
        testRemoveOutOfBoundsThrows();
        testRemoveOnEmptyListThrows();
        testSubListRemoveReturnsAndUpdatesRoot();
        testSubListRemoveOutOfBoundsThrows();
        testSubListRemoveAfterStructuralChangeThrowsCME();
    }
}
