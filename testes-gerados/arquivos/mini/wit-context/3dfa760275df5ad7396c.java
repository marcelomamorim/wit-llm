/*
 * @test
 * @summary Test LinkedBlockingDeque.tryAdvance behavior including null action and normal iteration
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceEmptyDeque();
        testTryAdvanceWithElements();
    }

    static void testTryAdvanceNullAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean advanced = deque.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new RuntimeException("Should not be called on empty deque");
            }
        });
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false on empty deque");
        }
    }

    static void testTryAdvanceWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = deque.tryAdvance(consumer);
        if (!advanced1 || !sb.toString().equals("one")) {
            throw new RuntimeException("tryAdvance failed to accept first element");
        }

        sb.setLength(0);
        boolean advanced2 = deque.tryAdvance(consumer);
        if (!advanced2 || !sb.toString().equals("two")) {
            throw new RuntimeException("tryAdvance failed to accept second element");
        }

        sb.setLength(0);
        boolean advanced3 = deque.tryAdvance(consumer);
        if (advanced3) {
            throw new RuntimeException("tryAdvance should return false after exhausting elements");
        }
    }
}
