/* @test    @summary Test LinkedList.pop() method for correct behavior    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testPopWithElements();
        testPopOnEmptyList();
    }

    private static void testPopWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String result = list.pop();
        if (!"a".equals(result)) {
            throw new AssertionError("Expected 'a', but got: " + result);
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after pop, but is: " + list.size());
        }
        if (!list.getFirst().equals("b")) {
            throw new AssertionError("First element should be 'b', but is: " + list.getFirst());
        }
    }

    private static void testPopOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.pop();
            throw new AssertionError("Expected NoSuchElementException when popping from empty list");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
        }
    }
}
