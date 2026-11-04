/* @test    @summary Test for LinkedList.set method to verify IndexOutOfBoundsException for invalid indices    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testSetWithNegativeIndex();
        testSetWithIndexEqualToSize();
        testSetWithValidIndex();
    }

    private static void testSetWithNegativeIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        try {
            list.set(-1, "x");
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testSetWithIndexEqualToSize() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        int size = list.size();
        try {
            list.set(size, "x");
            System.out.println("FAIL: Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for index equal to size");
        }
    }

    private static void testSetWithValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        try {
            String oldVal = list.set(1, "x");
            if (oldVal.equals("b") && list.get(1).equals("x")) {
                System.out.println("PASS: Successfully set element at valid index");
            } else {
                System.out.println("FAIL: Unexpected behavior in set at valid index");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
