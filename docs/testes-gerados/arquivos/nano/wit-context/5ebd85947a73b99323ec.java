/* @test    @summary Test for LinkedList.element() method to verify behavior when list is empty.    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testElementOnEmptyList();
        testElementOnNonEmptyList();
    }

    private static void testElementOnEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.element();
            System.out.println("FAIL: Expected NoSuchElementException not thrown");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("PASS: NoSuchElementException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testElementOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("test");
        try {
            Object result = list.element();
            if ("test".equals(result)) {
                System.out.println("PASS: element() returned expected value");
            } else {
                System.out.println("FAIL: element() returned unexpected value: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
