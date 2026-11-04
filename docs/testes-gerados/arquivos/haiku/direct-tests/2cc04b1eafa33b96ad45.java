/*
 * @test
 * @summary Tests ArrayList.SubList.remove(int) through the public subList API: return value, parent update, bounds check and comodification check
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveReturnsElementAndUpdatesParent();
        testRemoveNullElement();
        testRemoveOutOfBoundsThrows();
        testRemoveAfterStructuralModificationThrows();
    }

    static void testRemoveReturnsElementAndUpdatesParent() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        List<String> sub = list.subList(1, 3);
        String removed = sub.remove(0);
        if (!"b".equals(removed)) {
            throw new RuntimeException("expected b but was " + removed);
        }
        if (sub.size() != 1) {
            throw new RuntimeException("expected sublist size 1 but was " + sub.size());
        }
        if (!list.equals(Arrays.asList("a", "c", "d"))) {
            throw new RuntimeException("unexpected parent list " + list);
        }
    }

    static void testRemoveNullElement() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("x", null, "y"));
        List<String> sub = list.subList(0, 2);
        String removed = sub.remove(1);
        if (removed != null) {
            throw new RuntimeException("expected null but was " + removed);
        }
        if (!list.equals(Arrays.asList("x", "y"))) {
            throw new RuntimeException("unexpected parent list " + list);
        }
    }

    static void testRemoveOutOfBoundsThrows() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> sub = list.subList(0, 2);
        boolean thrown = false;
        try {
            sub.remove(2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IndexOutOfBoundsException");
        }
        if (list.size() != 3) {
            throw new RuntimeException("parent modified by failed remove");
        }
    }

    static void testRemoveAfterStructuralModificationThrows() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> sub = list.subList(0, 2);
        list.add("d");
        boolean thrown = false;
        try {
            sub.remove(0);
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ConcurrentModificationException");
        }
    }
}
