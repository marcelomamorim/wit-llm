/*
 * @test
 * @summary Witup-generated behavioral tests for LinkedBlockingDeque.size()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        check(d.size() == 0, "empty deque size must be 0");

        d.addLast(1);
        check(d.size() == 1, "size after addLast must be 1");

        d.offerFirst(2);
        d.putLast(3);
        check(d.size() == 3, "size after offerFirst and putLast must be 3");

        d.pollFirst();
        check(d.size() == 2, "size after pollFirst must be 2");

        d.removeLast();
        check(d.size() == 1, "size after removeLast must be 1");

        d.clear();
        check(d.size() == 0, "size after clear must be 0");

        LinkedBlockingDeque<Integer> bounded = new LinkedBlockingDeque<>(2);
        bounded.add(1);
        bounded.add(2);
        check(bounded.size() == 2, "bounded size must reflect inserted elements");
        check(!bounded.offer(3), "offer on full bounded deque must return false");
        check(bounded.size() == 2, "failed offer must not change size");

        LinkedBlockingDeque<Integer> fromCollection =
                new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        check(fromCollection.size() == 3, "size of deque built from collection must be 3");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
