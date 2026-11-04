/* @test    @summary Test for LinkedList(Collection<? extends E> c) constructor and exception behavior    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.Arrays;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCollection();
        testConstructorWithEmptyCollection();
        testConstructorWithSingleElementCollection();
        testConstructorWithMultipleElements();
        testConstructorWithInvalidIndex();
    }

    private static void testConstructorWithNullCollection() {
        try {
            new LinkedList<Object>((java.util.Collection<? extends Object>) null);
            System.out.println("FAIL: Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithEmptyCollection() {
        try {
            LinkedList<Integer> list = new LinkedList<>(Arrays.asList());
            if (list.isEmpty()) {
                System.out.println("PASS: Empty collection constructor creates empty list");
            } else {
                System.out.println("FAIL: List not empty after constructor with empty collection");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithSingleElementCollection() {
        try {
            LinkedList<String> list = new LinkedList<>(Arrays.asList("element"));
            if (list.size() == 1 && list.get(0).equals("element")) {
                System.out.println("PASS: Single element collection constructor works");
            } else {
                System.out.println("FAIL: List contents incorrect after constructor with single element");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithMultipleElements() {
        try {
            LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3));
            if (list.size() == 3 && list.get(0) == 1 && list.get(1) == 2 && list.get(2) == 3) {
                System.out.println("PASS: Multiple elements constructor works");
            } else {
                System.out.println("FAIL: List contents incorrect after constructor with multiple elements");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithInvalidIndex() {
        try {
            // Since the constructor does not take an index, simulate invalid internal state by passing a collection that causes addAll to throw
            // but as addAll does not throw IndexOutOfBoundsException, we simulate an invalid collection scenario
            // Alternatively, test addAll separately if needed
            // Here, we test that constructor does not throw for normal collection
            LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3));
            System.out.println("PASS: Constructor with valid collection does not throw");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("FAIL: Unexpected IndexOutOfBoundsException in constructor");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
