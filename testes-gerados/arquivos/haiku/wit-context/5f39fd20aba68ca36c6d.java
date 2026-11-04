/*
 * @test
 * @summary Witup-derived checks for ArrayList ListItr.add(E) via public listIterator(): cursor insertion and fail-fast ConcurrentModificationException
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddInsertsAtCursor();
        testAddThrowsCMEWhenListStructurallyModified();
    }

    static void testAddInsertsAtCursor() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "c"));
        ListIterator<String> it = list.listIterator();
        if (!"a".equals(it.next())) {
            throw new RuntimeException("unexpected first element");
        }
        it.add("b");
        if (!"[a, b, c]".equals(list.toString())) {
            throw new RuntimeException("add did not insert at cursor: " + list);
        }
        if (!"c".equals(it.next())) {
            throw new RuntimeException("cursor not advanced past inserted element");
        }
    }

    static void testAddThrowsCMEWhenListStructurallyModified() {
        List<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        list.add("b");
        boolean thrown = false;
        try {
            it.add("x");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ConcurrentModificationException");
        }
        if (!"[a, b]".equals(list.toString())) {
            throw new RuntimeException("list modified despite CME: " + list);
        }
    }
}
