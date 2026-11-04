/*
 * @test
 * @summary Witup-style checks for Vector iterator forEachRemaining behavior
 * @run main VectorWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class VectorWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullAction() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        Iterator<Integer> it = v.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testIteratesAllElements() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        Iterator<Integer> it = v.iterator();
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(x -> out.add(x));
        check(out.equals(java.util.Arrays.asList(1, 2, 3)), "expected [1, 2, 3] but got " + out);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }

    static void testRemainingAfterNext() {
        Vector<Integer> v = new Vector<>();
        v.add(10);
        v.add(20);
        v.add(30);
        Iterator<Integer> it = v.iterator();
        check(it.next() == 10, "first next() should return 10");
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(x -> out.add(x));
        check(out.equals(java.util.Arrays.asList(20, 30)), "expected remaining [20, 30] but got " + out);
    }

    static void testEmptyVector() {
        Vector<Integer> v = new Vector<>();
        Iterator<Integer> it = v.iterator();
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(x -> out.add(x));
        check(out.isEmpty(), "action should not be invoked for empty vector");
    }

    static void testStructuralModificationInAction() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        Iterator<Integer> it = v.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(x -> v.add(99));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }

    public static void main(String[] args) {
        testNullAction();
        testIteratesAllElements();
        testRemainingAfterNext();
        testEmptyVector();
        testStructuralModificationInAction();
    }
}
