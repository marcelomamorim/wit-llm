/* @test    @summary Test removeLast method of LinkedList for normal and exceptional behavior    @run main LinkedListWitupTest_P599126b14fb7 */

import java.util.LinkedList;

public class LinkedListWitupTest_P599126b14fb7 {

    public static void main(String[] args) {
        testRemoveLastEmpty();
        testRemoveLastWithElements();
    }

    private static void testRemoveLastEmpty() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testRemoveLastWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        String last = list.removeLast();
        if (!"b".equals(last)) {
            throw new AssertionError("Expected 'b', got: " + last);
        }
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new AssertionError("List state incorrect after removeLast");
        }
    }
}
