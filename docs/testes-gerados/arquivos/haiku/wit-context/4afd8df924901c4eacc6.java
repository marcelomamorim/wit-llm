/*
 * @test
 * @summary Checks observable behavior of LinkedBlockingDeque.peekFirst on empty, bounded and populated deques
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(d.peekFirst() == null, "empty deque peekFirst must return null");
        check(d.size() == 0, "peekFirst must not change size of empty deque");

        d.addFirst("a");
        check("a".equals(d.peekFirst()), "peekFirst must return the first element");
        check(d.size() == 1, "peekFirst must not remove the element");

        d.addLast("b");
        check("a".equals(d.peekFirst()), "peekFirst must still return the head after addLast");
        check(d.size() == 2, "size must be 2 after two insertions");

        check("a".equals(d.pollFirst()), "pollFirst must return the head");
        check("b".equals(d.peekFirst()), "peekFirst must return the new head");
        check(d.size() == 1, "size must be 1 after pollFirst");

        LinkedBlockingDeque<String> bounded = new LinkedBlockingDeque<>(1);
        check(bounded.peekFirst() == null, "empty bounded deque peekFirst must return null");
        bounded.add("x");
        check("x".equals(bounded.peekFirst()), "bounded deque peekFirst must return the element");
        check(bounded.size() == 1, "bounded deque size must remain 1");

        List<String> init = Arrays.asList("p", "q");
        LinkedBlockingDeque<String> fromCollection = new LinkedBlockingDeque<>(init);
        check("p".equals(fromCollection.peekFirst()), "peekFirst must return first element of source collection");
        check(fromCollection.size() == 2, "size must match source collection");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
