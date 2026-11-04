/*
 * @test
 * @summary Test for ArrayList.remove() method to verify exception paths and behavior.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsIllegalStateException();
        testRemoveDoesNotThrowWhenValid();
    }

    private static void testRemoveThrowsIllegalStateException() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ArrayList<Integer>.Itr iterator = list.new Itr();
        try {
            iterator.remove();
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRemoveDoesNotThrowWhenValid() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        ArrayList<Integer>.Itr iterator = list.new Itr();
        try {
            iterator.next(); // move cursor
            iterator.lastRet = 0; // simulate valid lastRet
            iterator.remove();
            if (list.size() == 2 && list.get(0) == 20) {
                System.out.println("PASS: remove() executed successfully.");
            } else {
                System.out.println("FAIL: List state unexpected after remove().");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during remove(): " + e);
        }
    }
}
