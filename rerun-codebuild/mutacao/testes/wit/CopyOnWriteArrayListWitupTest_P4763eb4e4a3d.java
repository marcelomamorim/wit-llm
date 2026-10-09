/*
 * @test    @summary Test for CopyOnWriteArrayList.listIterator(int) with valid and invalid indices
 * @run main CopyOnWriteArrayListWitupTest_P4763eb4e4a3d
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;

public class CopyOnWriteArrayListWitupTest_P4763eb4e4a3d {

    public static void main(String[] args) {
        testListIteratorValidIndex();
        testListIteratorNegativeIndex();
        testListIteratorIndexGreaterThanSize();
    }

    static void testListIteratorValidIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            ListIterator<String> it = list.listIterator(1);
            if (it.next().equals("b") && it.next().equals("c")) {
                System.out.println("testListIteratorValidIndex passed");
            } else {
                System.out.println("testListIteratorValidIndex failed: unexpected iteration order");
            }
        } catch (Exception e) {
            System.out.println("testListIteratorValidIndex failed: " + e);
        }
    }

    static void testListIteratorNegativeIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            list.listIterator(-1);
            System.out.println("testListIteratorNegativeIndex failed: no exception thrown");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testListIteratorNegativeIndex passed");
        } catch (Exception e) {
            System.out.println("testListIteratorNegativeIndex failed: " + e);
        }
    }

    static void testListIteratorIndexGreaterThanSize() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            list.listIterator(4);
            System.out.println("testListIteratorIndexGreaterThanSize failed: no exception thrown");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testListIteratorIndexGreaterThanSize passed");
        } catch (Exception e) {
            System.out.println("testListIteratorIndexGreaterThanSize failed: " + e);
        }
    }
}
