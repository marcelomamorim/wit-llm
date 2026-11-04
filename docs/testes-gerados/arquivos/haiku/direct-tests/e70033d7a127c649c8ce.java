/*
 * @test
 * @summary Verify LinkedBlockingDeque.peekLast returns the last element without removing it
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(d.peekLast() == null, "empty deque should return null");

        d.addLast("a");
        check("a".equals(d.peekLast()), "single element should be returned");
        check(d.size() == 1, "peekLast must not remove the element");

        d.addLast("b");
        check("b".equals(d.peekLast()), "peekLast should return last added element");

        d.addFirst("z");
        check("b".equals(d.peekLast()), "addFirst must not change peekLast");
        check(d.size() == 3, "size should be 3 after peeks");

        check("b".equals(d.pollLast()), "pollLast should return b");
        check("a".equals(d.peekLast()), "peekLast should return a after pollLast");

        d.pollLast();
        check("z".equals(d.peekLast()), "peekLast should return z");
        d.pollLast();
        check(d.peekLast() == null, "peekLast should be null after draining");

        LinkedBlockingDeque<Integer> bounded = new LinkedBlockingDeque<>(2);
        check(bounded.peekLast() == null, "bounded empty deque should return null");
        bounded.offerLast(1);
        bounded.offerLast(2);
        check(Integer.valueOf(2).equals(bounded.peekLast()), "bounded peekLast should return 2");
        check(bounded.size() == 2, "bounded size should remain 2");

        LinkedBlockingDeque<String> fromColl =
            new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check("y".equals(fromColl.peekLast()), "collection-constructed peekLast should return y");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
