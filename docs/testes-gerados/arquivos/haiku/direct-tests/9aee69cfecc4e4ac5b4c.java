/*
 * @test
 * @summary Behavioral tests for ArrayList iterator remove() (Itr.remove) via public iterator API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalState();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrowsIllegalState();
        testRemoveAfterStructuralModificationThrowsCME();
        testRemoveEvenElementsDuringIteration();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextThrowsIllegalState() {
        List<String> list = new ArrayList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(list.size() == 1, "list must be unchanged");
        check("a".equals(list.get(0)), "element must be unchanged");
    }

    static void testRemoveAfterNextRemovesElement() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Iterator<String> it = list.iterator();
        check("a".equals(it.next()), "first next() must be a");
        it.remove();
        check(list.size() == 2, "size must be 2 after remove");
        check("b".equals(list.get(0)), "index 0 must be b");
        check("c".equals(list.get(1)), "index 1 must be c");
    }

    static void testSecondRemoveWithoutNextThrowsIllegalState() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(list.size() == 1, "only one removal must have occurred");
        check("b".equals(list.get(0)), "remaining element must be b");
    }

    static void testRemoveAfterStructuralModificationThrowsCME() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        it.next();
        list.add("x");
        try {
            it.remove();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
        check(list.size() == 3, "list must keep the external add and no removal");
    }

    static void testRemoveEvenElementsDuringIteration() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            list.add(i);
        }
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer v = it.next();
            if (v % 2 == 0) {
                it.remove();
            }
        }
        check(list.size() == 2, "size must be 2");
        check(list.get(0) == 1, "index 0 must be 1");
        check(list.get(1) == 3, "index 1 must be 3");
    }
}
