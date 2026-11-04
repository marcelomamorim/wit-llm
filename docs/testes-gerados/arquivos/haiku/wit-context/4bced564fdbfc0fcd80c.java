/*
 * @test
 * @summary Witup-generated checks for LinkedBlockingDeque.contains
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        LinkedBlockingDeque<String> empty = new LinkedBlockingDeque<>();
        check(!empty.contains(null), "contains(null) on empty deque must be false");
        check(!empty.contains("a"), "contains on empty deque must be false");

        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(Arrays.asList("a", "b"));
        check(d.contains("a"), "present element a not found");
        check(d.contains("b"), "present element b not found");
        check(!d.contains("c"), "absent element c reported as present");
        check(!d.contains(null), "contains(null) must be false");
        check(d.contains(new String("a")), "contains must use equals semantics");

        d.removeFirst();
        check(!d.contains("a"), "removed element still reported as present");
        check(d.contains("b"), "remaining element not found");

        LinkedBlockingDeque<Integer> bounded = new LinkedBlockingDeque<>(2);
        bounded.offer(1);
        check(bounded.contains(1), "bounded deque element not found");
        check(bounded.contains(Integer.valueOf(1)), "boxed equal element not found");
        check(!bounded.contains(2), "absent integer reported as present");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
