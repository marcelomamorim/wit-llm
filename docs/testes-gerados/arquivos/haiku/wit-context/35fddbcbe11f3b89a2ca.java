/*
 * @test
 * @summary CopyOnWriteArrayList snapshot iterators must reject remove() with UnsupportedOperationException
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testIteratorRemoveOnEmptyList();
        testIteratorRemoveAfterNextLeavesListAndIteratorUnchanged();
        testListIteratorRemoveThrows();
    }

    static void expectUnsupported(Runnable r, String msg) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError(msg + ": expected UnsupportedOperationException");
    }

    static void testIteratorRemoveOnEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Iterator<String> it = list.iterator();
        expectUnsupported(it::remove, "remove on empty list iterator");
        if (!list.isEmpty()) {
            throw new AssertionError("list must remain empty");
        }
    }

    static void testIteratorRemoveAfterNextLeavesListAndIteratorUnchanged() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = list.iterator();
        if (!"a".equals(it.next())) {
            throw new AssertionError("first element expected to be a");
        }
        expectUnsupported(it::remove, "remove after next");
        if (list.size() != 3
                || !"a".equals(list.get(0))
                || !"b".equals(list.get(1))
                || !"c".equals(list.get(2))) {
            throw new AssertionError("list must be unchanged after failed remove");
        }
        if (!it.hasNext() || !"b".equals(it.next())) {
            throw new AssertionError("iterator must continue with b after failed remove");
        }
    }

    static void testListIteratorRemoveThrows() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(Arrays.asList("x", "y"));
        ListIterator<String> lit = list.listIterator();
        expectUnsupported(lit::remove, "remove on list iterator before next");
        lit.next();
        expectUnsupported(lit::remove, "remove on list iterator after next");
        if (list.size() != 2 || !"x".equals(list.get(0)) || !"y".equals(list.get(1))) {
            throw new AssertionError("list must be unchanged after failed ListIterator.remove");
        }
    }
}
