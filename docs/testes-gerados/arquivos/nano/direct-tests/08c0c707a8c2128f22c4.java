/* @test    @summary Test LinkedList.add(int, E) method for correct insertion at various positions    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAddAtBeginning();
        testAddAtEnd();
        testAddAtMiddle();
    }

    private static void testAddAtBeginning() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "first");
        assert list.size() == 1 : "Size should be 1 after adding at beginning";
        assert "first".equals(list.get(0)) : "Element at index 0 should be 'first'";
    }

    private static void testAddAtEnd() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "first");
        list.add(1, "second");
        assert list.size() == 2 : "Size should be 2 after adding at end";
        assert "second".equals(list.get(1)) : "Element at index 1 should be 'second'";
    }

    private static void testAddAtMiddle() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "first");
        list.add(1, "second");
        list.add(1, "middle");
        assert list.size() == 3 : "Size should be 3 after adding in middle";
        assert "middle".equals(list.get(1)) : "Element at index 1 should be 'middle'";
        assert "second".equals(list.get(2)) : "Element at index 2 should be 'second'";
    }
}
