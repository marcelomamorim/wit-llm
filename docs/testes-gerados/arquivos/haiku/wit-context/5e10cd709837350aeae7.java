/*
 * @test
 * @summary Tests ArrayList.SubList.add(int, E) behavior through the public subList API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddAtBoundsAndMiddle();
        testNegativeIndexThrows();
        testIndexAboveSizeThrows();
        testComodificationThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectThrows(Class<? extends Throwable> type, Runnable action, String message) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException(message + " (unexpected: " + t + ")", t);
        }
        throw new RuntimeException(message + " (no exception thrown)");
    }

    static void testAddAtBoundsAndMiddle() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> sub = list.subList(0, 2);
        sub.add(0, "x");
        sub.add(sub.size(), "y");
        check(sub.size() == 4, "sub size after adds: " + sub.size());
        check(list.toString().equals("[x, a, b, y, c]"), "unexpected list: " + list);
    }

    static void testNegativeIndexThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        final List<String> sub = list.subList(0, 1);
        expectThrows(IndexOutOfBoundsException.class,
                () -> sub.add(-1, "z"),
                "negative index must throw IndexOutOfBoundsException");
        check(list.toString().equals("[a, b]"), "list modified by failed add: " + list);
    }

    static void testIndexAboveSizeThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        final List<String> sub = list.subList(0, 1);
        expectThrows(IndexOutOfBoundsException.class,
                () -> sub.add(2, "z"),
                "index above sublist size must throw IndexOutOfBoundsException");
        check(list.toString().equals("[a, b]"), "list modified by failed add: " + list);
    }

    static void testComodificationThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        final List<String> sub = list.subList(0, 1);
        list.add("q");
        expectThrows(ConcurrentModificationException.class,
                () -> sub.add(0, "z"),
                "structural change of backing list must throw ConcurrentModificationException");
    }
}
