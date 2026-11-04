/* @test    @summary Test for LinkedList.remove() method to ensure correct removal and exception handling    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveMethodRemovesElement();
        testRemoveMethodThrowsIllegalStateException();
    }

    static void testRemoveMethodRemovesElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.iterator().next(); // advance iterator
        list.remove();
        assert list.size() == 2 : "List size should be 2 after removal";
        assert !list.contains("A") : "List should not contain 'A'";
    }

    static void testRemoveMethodThrowsIllegalStateException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        try {
            list.remove(); // should throw IllegalStateException because no lastReturned
            assert false : "Expected IllegalStateException was not thrown";
        } catch (IllegalStateException e) {
            // expected exception
        }
    }
}
