/* @test    @summary Test adding an element to LinkedList    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAddElement();
    }

    public static void testAddElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element1");
        if (list.size() != 1) {
            throw new AssertionError("Expected list size 1, but got " + list.size());
        }
        if (!"element1".equals(list.get(0))) {
            throw new AssertionError("Expected element 'element1', but got " + list.get(0));
        }
    }
}
