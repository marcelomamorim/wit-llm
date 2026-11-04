/*
 * @test
 * @summary Behavioral tests for ArrayList.subList(...).indexOf(Object), which delegates to root.indexOfRange and checkForComodification
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

    static void testBasicIndexInsideSubList() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        root.add(null);
        root.add("b");
        List<String> sub = root.subList(1, 4);
        check(sub.indexOf("b") == 0, "expected index 0 for first 'b' in sublist");
        check(sub.indexOf(null) == 1, "expected index 1 for null in sublist");
    }

    static void testAbsentElementReturnsMinusOne() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        root.add("c");
        root.add("d");
        List<String> sub = root.subList(1, 3);
        check(sub.indexOf("z") == -1, "absent element must return -1");
    }

    static void testElementOutsideRangeNotFound() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        root.add("c");
        root.add("d");
        List<String> sub = root.subList(2, 4);
        check(sub.indexOf("c") == 0, "expected index 0 for 'c'");
        check(sub.indexOf("a") == -1, "element outside sublist range must not be found");
        check(sub.indexOf("b") == -1, "element before sublist offset must not be found");
    }

    static void testEmptySubList() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        List<String> sub = root.subList(1, 1);
        check(sub.indexOf("a") == -1, "empty sublist must return -1");
        check(sub.indexOf(null) == -1, "empty sublist must return -1 for null");
    }

    static void testFirstOccurrenceReturned() {
        ArrayList<String> root = new ArrayList<>();
        root.add("x");
        root.add("y");
        root.add("x");
        check(root.subList(0, 3).indexOf("x") == 0, "first occurrence from start expected");
        check(root.subList(1, 3).indexOf("x") == 1, "first occurrence within offset sublist expected");
    }

    static void testComodificationDetected() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        List<String> sub = root.subList(0, 2);
        root.add("z");
        boolean thrown = false;
        try {
            sub.indexOf("a");
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification of root must cause ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testBasicIndexInsideSubList();
        testAbsentElementReturnsMinusOne();
        testElementOutsideRangeNotFound();
        testEmptySubList();
        testFirstOccurrenceReturned();
        testComodificationDetected();
    }
}
