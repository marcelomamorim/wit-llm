/*
 * @test
 * @summary Test LinkedList.pop() method for normal and exceptional behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testPopOnNonEmptyList();
        testPopOnEmptyListThrows();
        System.out.println("All tests passed.");
    }

    private static void testPopOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String popped = list.pop();
        if (!"first".equals(popped)) {
            throw new AssertionError("pop() did not return the first element as expected");
        }
        if (list.size() != 1 || !"second".equals(list.getFirst())) {
            throw new AssertionError("List state incorrect after pop()");
        }
    }

    private static void testPopOnEmptyListThrows() {
        LinkedList<Object> emptyList = new LinkedList<>();
        try {
            emptyList.pop();
            throw new AssertionError("pop() on empty list did not throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
