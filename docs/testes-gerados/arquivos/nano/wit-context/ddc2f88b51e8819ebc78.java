/*
 * @test
 * @summary Test for ArrayList.add(int, E) method, focusing on IndexOutOfBoundsException.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddWithNegativeIndex();
        testAddWithIndexGreaterThanSize();
        testAddWithValidIndex();
    }

    private static void testAddWithNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(-1, "b");
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testAddWithIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        int index = list.size() + 1;
        try {
            list.add(index, "b");
            System.out.println("FAIL: Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for index > size");
        }
    }

    private static void testAddWithValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(0, "b");
            if (list.size() == 2 && list.get(0).equals("b") && list.get(1).equals("a")) {
                System.out.println("PASS: Successfully added element at valid index");
            } else {
                System.out.println("FAIL: Element not added correctly at valid index");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception at valid index: " + e);
        }
    }
}
