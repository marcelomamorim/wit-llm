/*
 * @test
 * @summary Test LinkedList.getFirst() for normal and empty list cases
 * @run main LinkedListWitupTest_Pa0b406b319de
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_Pa0b406b319de {
    public static void main(String[] args) {
        testGetFirstNonEmpty();
        testGetFirstEmpty();
    }

    private static void testGetFirstNonEmpty() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String first = list.getFirst();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first' but got: " + first);
        }
    }

    private static void testGetFirstEmpty() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException for empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
