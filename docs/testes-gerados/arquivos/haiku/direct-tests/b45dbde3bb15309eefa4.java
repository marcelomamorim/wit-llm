/*
 * @test
 * @summary Tests SubList ListIterator.add via public ArrayList.subList(...).listIterator(...) API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAddInsertsAtCursor() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("c");
        List<String> sub = root.subList(0, 2);
        ListIterator<String> it = sub.listIterator(1);
        it.add("b");
        check(root.size() == 3, "root size after add: " + root.size());
        check(root.get(0).equals("a"), "index 0");
        check(root.get(1).equals("b"), "index 1 should be b");
        check(root.get(2).equals("c"), "index 2");
        check(sub.size() == 3, "sublist size after add: " + sub.size());
        check(it.nextIndex() == 2, "nextIndex after add: " + it.nextIndex());
    }

    static void testAddAtEnd() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        List<String> sub = root.subList(0, root.size());
        ListIterator<String> it = sub.listIterator(sub.size());
        it.add("z");
        check(root.size() == 2, "size after add at end");
        check(root.get(1).equals("z"), "last element should be z");
    }

    static void testConsecutiveAddsPreserveOrder() {
        ArrayList<String> root = new ArrayList<>();
        root.add("s");
        List<String> sub = root.subList(0, 1);
        ListIterator<String> it = sub.listIterator(1);
        it.add("p");
        it.add("q");
        check(root.size() == 3, "size after two adds: " + root.size());
        check(root.get(0).equals("s"), "index 0");
        check(root.get(1).equals("p"), "index 1");
        check(root.get(2).equals("q"), "index 2");
    }

    static void testComodificationDetected() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        List<String> sub = root.subList(0, 1);
        ListIterator<String> it = sub.listIterator(1);
        root.add("x");
        boolean thrown = false;
        try {
            it.add("y");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural change to root");
    }

    public static void main(String[] args) {
        testAddInsertsAtCursor();
        testAddAtEnd();
        testConsecutiveAddsPreserveOrder();
        testComodificationDetected();
    }
}
