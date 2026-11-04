/* @test    @summary Test removeLast() method of LinkedList for normal and exceptional behavior    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveLastEmpty();
        testRemoveLastWithElements();
    }

    private static void testRemoveLastEmpty() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeLast();
            System.out.println("FAIL: Expected NoSuchElementException for empty list");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("PASS: Caught expected NoSuchElementException for empty list");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRemoveLastWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String lastElement = list.removeLast();
        if ("c".equals(lastElement) && list.size() == 2 && list.getLast().equals("b")) {
            System.out.println("PASS: removeLast() returned 'c' and list updated correctly");
        } else {
            System.out.println("FAIL: Unexpected behavior in removeLast() with elements");
        }
    }
}
