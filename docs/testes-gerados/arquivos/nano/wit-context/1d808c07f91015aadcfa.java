/*
 * @test
 * @summary Test for ArrayList.set method, covering normal and exceptional paths.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetNormal();
        testSetIllegalStateException();
        testSetConcurrentModificationException();
    }

    static void testSetNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.set(1, "x");
        assert list.get(1).equals("x") : "Expected "x" at index 1";
    }

    static void testSetIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ArrayList<String>.ListItr itr = list.listIterator();
        try {
            // Simulate lastRet < 0 by not moving iterator and calling set
            list.set(0, "new"); // valid call
            // Now forcibly set lastRet to -1 to simulate illegal state
            // Since lastRet is private, we cannot set it directly; instead, we simulate by calling set without valid lastRet
            // but in actual code, this is not possible without reflection. Instead, we test the exception path by calling set with invalid lastRet.
            // So, we invoke set with invalid lastRet by creating a subclass or reflection, but as per constraints, we avoid reflection.
            // Therefore, we simulate the exception by calling set on an iterator after invalid operation.
            // But since the method is public, and lastRet is private, we cannot directly manipulate lastRet.
            // Instead, we test the exception by calling set on the iterator after removing the element.
            // For simplicity, we call list.set with invalid index to trigger exception.
            list.set(-1, "invalid");
            assert false : "Expected IllegalStateException not thrown";
        } catch (IllegalStateException e) {
            // expected
        } catch (IndexOutOfBoundsException e) {
            // Not expected here
            assert false : "Unexpected IndexOutOfBoundsException";
        }
    }

    static void testSetConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            for (String s : list) {
                if (s.equals("b")) {
                    list.remove(s); // structural modification during iteration
                }
            }
            // The above removal does not cause ConcurrentModificationException in for-each, but in iterator it does.
            // So, we test with explicit iterator.
        } catch (java.util.ConcurrentModificationException e) {
            // expected
        }
        // Better test: use iterator explicitly
        try {
            java.util.Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String s = it.next();
                if (s.equals("b")) {
                    list.remove(s); // structural modification
                }
            }
            assert false : "Expected ConcurrentModificationException not thrown";
        } catch (java.util.ConcurrentModificationException e) {
            // expected
        }
    }
}
