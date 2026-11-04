/*
 * @test
 * @summary Test LinkedList.LLSpliterator.forEachRemaining behavior including null action and concurrent modification
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class LinkedListWitupTest {

    static class TestConsumer<E> implements Consumer<E> {
        int count = 0;
        public void accept(E e) {
            count++;
        }
    }

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
        testForEachRemainingConcurrentModification();
    }

    private static void testForEachRemainingNullAction() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        LinkedList.LLSpliterator<String> spliterator = list.spliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        LinkedList.LLSpliterator<String> spliterator = list.spliterator();
        TestConsumer<String> consumer = new TestConsumer<>();
        spliterator.forEachRemaining(consumer);
        if (consumer.count != 2) {
            throw new Error("Expected consumer to be called twice, but was called " + consumer.count + " times");
        }
    }

    private static void testForEachRemainingConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        LinkedList.LLSpliterator<String> spliterator = list.spliterator();
        list.add("b"); // structural modification after spliterator creation
        TestConsumer<String> consumer = new TestConsumer<>();
        try {
            spliterator.forEachRemaining(consumer);
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
