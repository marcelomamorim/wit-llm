/*
 * @test
 * @summary Behavioral tests for Vector.remove(int)
 * @run main VectorWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

public class VectorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveMiddle() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        v.add("d");
        String r = v.remove(1);
        check("b".equals(r), "removed value should be b");
        check(v.size() == 3, "size should be 3");
        check("a".equals(v.get(0)), "index 0 should be a");
        check("c".equals(v.get(1)), "index 1 should be c");
        check("d".equals(v.get(2)), "index 2 should be d");
    }

    static void testRemoveFirst() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        String r = v.remove(0);
        check("a".equals(r), "removed value should be a");
        check(v.size() == 1, "size should be 1");
        check("b".equals(v.get(0)), "remaining element should be b");
    }

    static void testRemoveLast() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        String r = v.remove(2);
        check("c".equals(r), "removed value should be c");
        check(v.size() == 2, "size should be 2");
        check("a".equals(v.get(0)), "index 0 should be a");
        check("b".equals(v.get(1)), "index 1 should be b");
    }

    static void testRemoveSingle() {
        Vector<String> v = new Vector<>();
        v.add("x");
        String r = v.remove(0);
        check("x".equals(r), "removed value should be x");
        check(v.isEmpty(), "vector should be empty");
    }

    static void testRemoveNullElement() {
        Vector<String> v = new Vector<>();
        v.add(null);
        v.add("y");
        String r = v.remove(0);
        check(r == null, "removed null element should return null");
        check(v.size() == 1, "size should be 1");
        check("y".equals(v.get(0)), "remaining element should be y");
    }

    static void testOutOfBoundsIndexEqualsSize() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        boolean thrown = false;
        try {
            v.remove(2);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "remove(size) should throw ArrayIndexOutOfBoundsException");
        check(v.size() == 2, "size should be unchanged after failed remove");
        check("a".equals(v.get(0)) && "b".equals(v.get(1)), "elements should be unchanged");
    }

    static void testOutOfBoundsOnEmpty() {
        Vector<String> v = new Vector<>();
        boolean thrown = false;
        try {
            v.remove(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "remove(0) on empty vector should throw ArrayIndexOutOfBoundsException");
    }

    static void testNegativeIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        boolean thrown = false;
        try {
            v.remove(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "remove(-1) should throw ArrayIndexOutOfBoundsException");
        check(v.size() == 1, "size should be unchanged after failed remove");
    }

    static void testFailFastIteratorAfterRemove() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        Iterator<String> it = v.iterator();
        it.next();
        v.remove(0);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "iterator should be fail-fast after remove(int)");
    }

    public static void main(String[] args) {
        testRemoveMiddle();
        testRemoveFirst();
        testRemoveLast();
        testRemoveSingle();
        testRemoveNullElement();
        testOutOfBoundsIndexEqualsSize();
        testOutOfBoundsOnEmpty();
        testNegativeIndex();
        testFailFastIteratorAfterRemove();
    }
}
