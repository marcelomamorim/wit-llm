/*
 * @test
 * @summary Checks public ArrayList.subList iterator next() behavior for exhaustion and comodification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextAfterExhaustionThrowsNoSuchElement();
        testNextAfterRootStructuralModificationThrowsCME();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testNextReturnsElementsInOrder() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> sub = list.subList(0, 2);
        Iterator<String> it = sub.iterator();
        if (!"a".equals(it.next())) fail("first element mismatch");
        if (!"b".equals(it.next())) fail("second element mismatch");
        if (it.hasNext()) fail("iterator should be exhausted");
    }

    static void testNextAfterExhaustionThrowsNoSuchElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.subList(0, 2).iterator();
        it.next();
        it.next();
        try {
            it.next();
            fail("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testNextAfterRootStructuralModificationThrowsCME() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Iterator<String> it = list.subList(0, 2).iterator();
        list.add("d");
        try {
            it.next();
            fail("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
