/* @test    @summary Test ArrayList.listIterator(int index) with valid and invalid indices    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testListIteratorWithValidIndex();
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexGreaterThanSize();
    }

    private static void testListIteratorWithValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int index = 1;
        ListIterator<String> iterator = list.listIterator(index);
        boolean hasNext = iterator.hasNext();
        String nextElement = hasNext ? iterator.next() : null;
        if (nextElement != null && nextElement.equals("b")) {
            System.out.println("PASS: listIterator with valid index") ;
        } else {
            System.out.println("FAIL: listIterator with valid index") ;
        }
    }

    private static void testListIteratorWithNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        try {
            list.listIterator(-1);
            System.out.println("FAIL: listIterator with negative index did not throw") ;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: listIterator with negative index") ;
        }
    }

    private static void testListIteratorWithIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        try {
            list.listIterator(3);
            System.out.println("FAIL: listIterator with index greater than size did not throw") ;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: listIterator with index greater than size") ;
        }
    }
}
