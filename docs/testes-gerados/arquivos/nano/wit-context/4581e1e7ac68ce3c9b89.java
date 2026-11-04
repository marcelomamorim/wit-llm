/* @test    @summary Test for ArrayDeque.add(E e) method to verify behavior with null input.    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testAddWithNull();
        testAddWithValidElement();
    }

    private static void testAddWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.add(null);
            // In Java 11, ArrayDeque.add(null) throws NullPointerException
            // If no exception, test fails
            System.err.println("Failed: add(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            System.out.println("Passed: add(null) threw NullPointerException as expected");
        } catch (Exception e) {
            System.err.println("Failed: add(null) threw unexpected exception: " + e);
        }
    }

    private static void testAddWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.add("element");
        if (result && deque.contains("element")) {
            System.out.println("Passed: add("element") succeeded and element is in deque");
        } else {
            System.err.println("Failed: add("element") did not behave as expected");
        }
    }
}
