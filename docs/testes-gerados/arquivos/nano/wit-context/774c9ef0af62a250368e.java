/*
 * @test
 * @summary Tests for ArrayList.subList(int, int)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListWithNegativeFromIndex();
        testSubListWithFromIndexGreaterThanSize();
        testSubListWithFromIndexGreaterThanToIndex();
    }

    private static void testSubListWithNegativeFromIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.subList(-1, 1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative fromIndex");
        }
    }

    private static void testSubListWithFromIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.subList(3, 4);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for fromIndex > size");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for fromIndex > size");
        }
    }

    private static void testSubListWithFromIndexGreaterThanToIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.subList(1, 0);
            System.out.println("FAIL: Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for fromIndex > toIndex");
        }
    }
}
