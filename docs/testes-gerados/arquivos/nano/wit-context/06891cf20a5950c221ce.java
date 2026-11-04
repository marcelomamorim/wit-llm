/*
 * @test
 * @summary Test for LinkedList.tryAdvance with null Consumer
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.function.Consumer;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyList();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        boolean threwException = false;
        try {
            list.tryAdvance(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }

    private static void testTryAdvanceWithEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        Consumer<String> consumer = e -> {};
        boolean result = list.tryAdvance(consumer);
        if (result != false) {
            throw new AssertionError("Expected false when list is empty");
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element1");
        list.add("element2");
        Consumer<String> consumer = e -> {
            if (e == null) {
                throw new AssertionError("Element should not be null");
            }
        };
        boolean result = list.tryAdvance(consumer);
        if (result != true) {
            throw new AssertionError("Expected true when list has elements");
        }
    }
}
