/*
 * @test
 * @summary Checks ArrayList.SubList ListIterator.set(E) observable behavior: lastRet check and comodification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetAfterNextUpdatesBackingList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sub = list.subList(0, 3);
        ListIterator<Integer> it = sub.listIterator();
        check(it.next() == 1, "next should return 1");
        it.set(10);
        check(list.get(0) == 10, "set should update backing list at index 0");
        check(list.get(1) == 2, "other elements must be unchanged");
        check(list.size() == 3, "size must be unchanged by set");
    }

    static void testSetWithoutPriorNextThrowsIllegalState() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ListIterator<Integer> it = list.subList(0, 2).listIterator();
        boolean thrown = false;
        try {
            it.set(5);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set without next/previous must throw IllegalStateException");
        check(list.get(0) == 1 && list.get(1) == 2, "list must not change");
    }

    static void testSetAfterRemoveThrowsIllegalState() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ListIterator<Integer> it = list.subList(0, 2).listIterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.set(7);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set after remove must throw IllegalStateException");
    }

    static void testSetAfterStructuralModificationThrowsCME() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ListIterator<Integer> it = list.subList(0, 3).listIterator();
        it.next();
        list.add(4);
        boolean thrown = false;
        try {
            it.set(9);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "set after parent structural modification must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testSetAfterNextUpdatesBackingList();
        testSetWithoutPriorNextThrowsIllegalState();
        testSetAfterRemoveThrowsIllegalState();
        testSetAfterStructuralModificationThrowsCME();
    }
}
