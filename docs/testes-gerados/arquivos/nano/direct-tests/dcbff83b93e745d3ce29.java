/*
 * @test
 * @summary Test the set method of LinkedList for correct behavior and exception handling.
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testSetWithValidElement();
        testSetWithoutLastReturned();
    }

    private static void testSetWithValidElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("initial");
        list.add("second");
        list.set(0, "updated");
        assert "updated".equals(list.get(0)) : "Element at index 0 should be 'updated'";
    }

    private static void testSetWithoutLastReturned() {
        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        try {
            list.set("new");
            assert false : "Expected IllegalStateException when lastReturned is null";
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
