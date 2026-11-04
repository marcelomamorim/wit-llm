/*
 * @test
 * @summary WIT-derived checks for ArrayList subList iterator add() comodification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testIteratorAddWithoutConcurrentModification();
        testIteratorAddAfterRootStructuralModificationThrowsCME();
    }

    static void testIteratorAddWithoutConcurrentModification() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sub = list.subList(0, 2);
        ListIterator<Integer> it = sub.listIterator();
        it.add(0);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        if (!list.equals(expected)) {
            throw new RuntimeException("Unexpected list after iterator add: " + list);
        }
        if (sub.size() != 3) {
            throw new RuntimeException("Unexpected subList size: " + sub.size());
        }
    }

    static void testIteratorAddAfterRootStructuralModificationThrowsCME() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sub = list.subList(0, 2);
        ListIterator<Integer> it = sub.listIterator();
        list.add(99);
        boolean thrown = false;
        try {
            it.add(5);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException");
        }
    }
}
