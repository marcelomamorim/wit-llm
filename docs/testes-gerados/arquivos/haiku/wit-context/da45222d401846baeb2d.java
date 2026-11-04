/*
 * @test
 * @summary Verifies ListIterator.add on LinkedList throws ConcurrentModificationException after external structural modification and works otherwise
 * @run main LinkedListWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testIteratorAddThrowsAfterExternalStructuralModification();
        testIteratorAddWorksWithoutExternalModification();
    }

    static void testIteratorAddThrowsAfterExternalStructuralModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        list.add("b");
        try {
            it.add("x");
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testIteratorAddWorksWithoutExternalModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("c");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.add("b");
        if (list.size() != 3) {
            throw new AssertionError("Unexpected size: " + list.size());
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1)) || !"c".equals(list.get(2))) {
            throw new AssertionError("Unexpected contents: " + list);
        }
    }
}
