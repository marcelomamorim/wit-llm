/* @test    @summary Test tryAdvance method of LinkedList for correct behavior and exception handling    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.function.Consumer;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyList();
        testTryAdvanceWithNonEmptyList();
        testConcurrentModificationException();
    }

    static void testTryAdvanceWithNullConsumer() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        try {
            list.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testTryAdvanceWithEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        Consumer<String> consumer = s -> {};
        boolean result = list.tryAdvance(consumer);
        if (!result) {
            System.out.println("PASS: tryAdvance returns false on empty list");
        } else {
            System.out.println("FAIL: tryAdvance should return false on empty list");
        }
    }

    static void testTryAdvanceWithNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        Consumer<String> consumer = s -> {
            if (s.equals("a")) {
                System.out.println("PASS: Consumer accepted element 'a'");
            } else {
                System.out.println("FAIL: Consumer received unexpected element: " + s);
            }
        };
        boolean result = list.tryAdvance(consumer);
        if (result) {
            System.out.println("PASS: tryAdvance returns true when element processed");
        } else {
            System.out.println("FAIL: tryAdvance should return true when element is processed");
        }
    }

    static void testConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Consumer<String> consumer = s -> {
            if (s.equals("b")) {
                list.remove("b"); // structural modification during iteration
            }
        };
        try {
            list.tryAdvance(consumer);
            System.out.println("FAIL: Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            System.out.println("PASS: ConcurrentModificationException thrown as expected");
        }
    }
}
