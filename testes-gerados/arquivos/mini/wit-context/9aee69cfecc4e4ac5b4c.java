/*
 * @test
 * @summary Test java.util.ArrayList.Itr.remove() method for expected exceptions and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.IllegalStateException;
import java.util.Iterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIllegalStateException();
        testRemoveConcurrentModificationException();
        testRemoveNormalBehavior();
    }

    private static void testRemoveIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        try {
            // Directly call remove() without next() to cause IllegalStateException
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testRemoveConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        it.next();
        // Modify list structurally outside iterator
        list.add("b");
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected exception
        }
    }

    private static void testRemoveNormalBehavior() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if ("a".equals(s)) {
                it.remove();
            }
        }
        if (list.size() != 1 || !"b".equals(list.get(0))) {
            throw new RuntimeException("Remove did not work as expected");
        }
    }
}
