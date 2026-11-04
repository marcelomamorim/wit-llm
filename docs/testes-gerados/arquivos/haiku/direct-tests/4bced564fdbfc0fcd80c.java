/*
 * @test
 * @summary Witup-style tests for LinkedBlockingDeque.contains
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(!d.contains(null), "contains(null) must return false");
        check(!d.contains("a"), "empty deque must not contain \"a\"");

        d.add("a");
        d.addLast("b");
        d.addFirst("z");
        check(d.contains("a"), "middle/last element must be found");
        check(d.contains("b"), "last element must be found");
        check(d.contains("z"), "first element must be found");
        check(d.contains(new String("b")), "contains must use equals, not identity");
        check(!d.contains("q"), "absent element must not be found");
        check(!d.contains(Integer.valueOf(1)), "element of different type must not match");

        d.remove("a");
        check(!d.contains("a"), "removed element must not be found");
        check(d.contains("b"), "other elements must remain after removal");

        LinkedBlockingDeque<Integer> bounded = new LinkedBlockingDeque<>(2);
        check(!bounded.contains(1), "new bounded deque must be empty");
        bounded.offer(1);
        check(bounded.contains(1), "bounded deque must contain offered element");
        check(!bounded.contains(2), "bounded deque must not contain absent element");

        LinkedBlockingDeque<String> fromCollection =
                new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check(fromCollection.contains("x"), "collection constructor must keep elements");
        check(fromCollection.contains("y"), "collection constructor must keep last element");
        check(!fromCollection.contains(null), "contains(null) must stay false");
    }
}
