/* @test    @summary Test for Vector.listIterator(int index) method to verify IndexOutOfBoundsException behavior.    @run main VectorWitupTest_P27dc52d74b49 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest_P27dc52d74b49 {

    public static void main(String[] args) {
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexEqualToSize();
        testListIteratorWithValidIndex();
    }

    private static void testListIteratorWithNegativeIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        try {
            vector.listIterator(-1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testListIteratorWithIndexEqualToSize() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        int size = vector.size();
        try {
            vector.listIterator(size);
            System.out.println("PASS: listIterator with index equal to size succeeded");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("FAIL: Unexpected IndexOutOfBoundsException for index equal to size");
        }
    }

    private static void testListIteratorWithValidIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(10);
        vector.add(20);
        try {
            ListIterator<Integer> it = vector.listIterator(1);
            if (it.next() == 20) {
                System.out.println("PASS: listIterator with valid index returned correct iterator");
            } else {
                System.out.println("FAIL: Iterator did not return expected element");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("FAIL: Unexpected IndexOutOfBoundsException for valid index");
        }
    }
}
