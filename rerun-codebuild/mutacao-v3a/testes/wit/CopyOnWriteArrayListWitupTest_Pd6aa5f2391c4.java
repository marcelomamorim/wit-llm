/*
 * @test
 * @summary Tests for CopyOnWriteArrayList.listIterator(int index)
 * @run main CopyOnWriteArrayListWitupTest_Pd6aa5f2391c4
 */

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest_Pd6aa5f2391c4 {
    public static void main(String[] args) {
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexGreaterThanSize();
        testListIteratorWithValidIndex();
    }

    static void testListIteratorWithNegativeIndex() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        try {
            list.listIterator(-1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative index");
        }
    }

    static void testListIteratorWithIndexGreaterThanSize() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        int size = list.size();
        try {
            list.listIterator(size + 1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for index > size");
        }
    }

    static void testListIteratorWithValidIndex() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        int index = 1;
        ListIterator<Integer> iterator = list.listIterator(index);
        if (iterator.next().equals(20)) {
            System.out.println("PASS: listIterator with valid index returns correct element");
        } else {
            System.out.println("FAIL: listIterator with valid index returns incorrect element");
        }
        if (iterator.previous() == 20) {
            System.out.println("PASS: previous() works correctly after next()");
        } else {
            System.out.println("FAIL: previous() does not work as expected");
        }
    }
}
