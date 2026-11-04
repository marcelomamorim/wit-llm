/*
 * @test
 * @summary Behavioral tests for LinkedBlockingDeque.peek() on empty, bounded, and populated deques
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyPeekReturnsNull();
        testPeekReturnsHeadWithoutRemoving();
        testPeekAfterAddFirst();
        testBoundedDequePeek();
        testCollectionConstructorPeek();
        testPeekAfterPollFirst();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyPeekReturnsNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.peek() == null, "peek() on empty deque must return null");
        check(deque.size() == 0, "peek() must not change size of empty deque");
    }

    static void testPeekReturnsHeadWithoutRemoving() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        check("a".equals(deque.peek()), "peek() must return first element");
        check(deque.size() == 2, "peek() must not remove the element");
        check("a".equals(deque.peek()), "repeated peek() must return same head");
    }

    static void testPeekAfterAddFirst() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("x");
        deque.addFirst("y");
        check("y".equals(deque.peek()), "peek() must reflect addFirst");
    }

    static void testBoundedDequePeek() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        check(deque.peek() == null, "bounded empty deque peek must be null");
        deque.offerLast(Integer.valueOf(7));
        deque.offerLast(Integer.valueOf(8));
        check(Integer.valueOf(7).equals(deque.peek()), "bounded deque peek must return head");
        check(deque.size() == 2, "peek() must not alter bounded deque size");
    }

    static void testCollectionConstructorPeek() {
        List<String> source = Arrays.asList("first", "second");
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(source);
        check("first".equals(deque.peek()), "peek() must return first element copied from collection");
        check(deque.size() == 2, "collection-constructed deque must keep all elements after peek()");
    }

    static void testPeekAfterPollFirst() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("p");
        deque.addLast("q");
        check("p".equals(deque.pollFirst()), "pollFirst() must return p");
        check("q".equals(deque.peek()), "peek() must return new head after pollFirst()");
        deque.pollFirst();
        check(deque.peek() == null, "peek() must return null after draining deque");
    }
}
