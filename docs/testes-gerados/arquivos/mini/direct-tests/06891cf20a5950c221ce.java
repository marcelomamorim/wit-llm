/*
 * @test
 * @summary Test java.util.LinkedList.tryAdvance(Consumer) method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullConsumer();
        testTryAdvanceEmptyList();
        testTryAdvanceSingleElement();
        testTryAdvanceConcurrentModification();
    }

    static void testTryAdvanceNullConsumer() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.tryAdvance(null);
            throw new Error("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        boolean result = list.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new Error("Should not be called on empty list");
            }
        });
        if (result) {
            throw new Error("tryAdvance should return false on empty list");
        }
    }

    static void testTryAdvanceSingleElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");

        // We cannot directly access tryAdvance because it is not public in LinkedList
        // The provided method is public boolean tryAdvance(Consumer<? super E> action)
        // but in actual JDK 11 LinkedList, tryAdvance is not public.
        // However, per the problem statement, we assume the method is public.

        final StringBuilder sb = new StringBuilder();
        boolean advanced = list.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        if (!advanced) {
            throw new Error("tryAdvance should return true when element is present");
        }
        if (!"element".equals(sb.toString())) {
            throw new Error("Consumer did not receive the correct element");
        }

        // Second call should return false as no more elements
        boolean secondCall = list.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new Error("Should not be called on exhausted iterator");
            }
        });
        if (secondCall) {
            throw new Error("tryAdvance should return false after all elements are consumed");
        }
    }

    static void testTryAdvanceConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("one");

        // We need to simulate concurrent modification during tryAdvance
        // The tryAdvance method checks if list.modCount != expectedModCount after action.accept
        // We cannot access modCount or expectedModCount directly.
        // So we simulate by modifying the list inside the Consumer.accept

        try {
            list.tryAdvance(new Consumer<String>() {
                public void accept(String s) {
                    // Structural modification during iteration
                    list.add("two");
                }
            });
            throw new Error("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
