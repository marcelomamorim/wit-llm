/*
 * @test
 * @summary Test LinkedList.pop() method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testPopOnNonEmptyList();
        testPopOnEmptyList();
    }

    private static void testPopOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");

        String popped = list.pop();
        if (!"first".equals(popped)) {
            throw new AssertionError("Expected 'first' but got: " + popped);
        }

        if (list.size() != 1 || !"second".equals(list.getFirst())) {
            throw new AssertionError("List state incorrect after pop");
        }
    }

    private static void testPopOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.pop();
            throw new AssertionError("Expected NoSuchElementException when popping empty list");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
