/*
 * @test
 * @summary Test LinkedList.get(int) for valid and invalid indices
 * @run main java.util.LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("zero");
        list.add("one");
        list.add("two");

        // Test valid indices
        if (!"zero".equals(list.get(0))) {
            throw new AssertionError("Expected 'zero' at index 0");
        }
        if (!"one".equals(list.get(1))) {
            throw new AssertionError("Expected 'one' at index 1");
        }
        if (!"two".equals(list.get(2))) {
            throw new AssertionError("Expected 'two' at index 2");
        }

        // Test invalid indices: negative index
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test invalid indices: index equal to size
        try {
            list.get(3);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 3");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test invalid indices: index greater than size
        try {
            list.get(100);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 100");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
