/*
 * @test
 * @summary Observable behavior of LinkedBlockingDeque.peekLast
 * @run main LinkedBlockingDequeWitupTest
 */
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(d.peekLast() == null, "peekLast on empty deque must return null");

        d.addLast("a");
        check("a".equals(d.peekLast()), "single element must be returned");

        d.addLast("b");
        check("b".equals(d.peekLast()), "peekLast must return last added element");
        check(d.size() == 2, "peekLast must not remove elements");

        d.addFirst("z");
        check("b".equals(d.peekLast()), "addFirst must not change last element");

        d.pollLast();
        check("a".equals(d.peekLast()), "peekLast must reflect pollLast removal");

        d.clear();
        check(d.peekLast() == null, "peekLast after clear must return null");

        LinkedBlockingDeque<Integer> bounded = new LinkedBlockingDeque<>(1);
        check(bounded.peekLast() == null, "bounded empty deque must return null");
        bounded.offerLast(7);
        check(Integer.valueOf(7).equals(bounded.peekLast()), "bounded deque must return stored element");

        LinkedBlockingDeque<String> fromCollection =
            new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check("y".equals(fromCollection.peekLast()), "collection constructor must preserve order");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
