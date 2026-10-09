/* @test    @summary Test for CopyOnWriteArrayList.listIterator(int) method    @run main CopyOnWriteArrayListWitupTest_P7fb41260609a */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;

public class CopyOnWriteArrayListWitupTest_P7fb41260609a {

    public static void main(String[] args) {
        testListIteratorAtValidIndex();
        testListIteratorAtNegativeIndex();
        testListIteratorAtIndexGreaterThanSize();
        System.out.println("All tests passed.");
    }

    static void testListIteratorAtValidIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int index = 1;
        ListIterator<String> it = list.listIterator(index);
        if (it.nextIndex() != index) {
            throw new AssertionError("Expected nextIndex() to be " + index + " but was " + it.nextIndex());
        }
        if (!it.hasNext()) {
            throw new AssertionError("Expected hasNext() to be true");
        }
        String nextElem = it.next();
        if (!"b".equals(nextElem)) {
            throw new AssertionError("Expected next element to be 'b' but was '" + nextElem + "'");
        }
    }

    static void testListIteratorAtNegativeIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        try {
            list.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testListIteratorAtIndexGreaterThanSize() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("m");
        list.add("n");
        try {
            list.listIterator(3);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
