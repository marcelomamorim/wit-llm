/* @test    @summary Test for ArrayList.listIterator(int index) with invalid index values to trigger IndexOutOfBoundsException    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexEqualToSize();
    }

    private static void testListIteratorWithNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.listIterator(-1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testListIteratorWithIndexEqualToSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int size = list.size();
        try {
            list.listIterator(size);
            // Should succeed without exception
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Failed: Unexpected IndexOutOfBoundsException for index equal to size");
        }
    }
}
