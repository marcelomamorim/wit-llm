/*
 * @test
 * @summary Behavioral checks for LinkedBlockingQueue.contains(Object): null argument, empty queue, membership, removal, equals-based matching, and collection-backed construction.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final class EqualsProbe {
        @Override
        public boolean equals(Object other) {
            return "x".equals(other);
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        check(!q.contains(null), "contains(null) on empty queue must return false");
        check(!q.contains("a"), "contains on empty queue must return false");

        q.add("a");
        q.add("b");
        check(q.contains("a"), "contains must find first element");
        check(q.contains("b"), "contains must find last element");
        check(!q.contains("c"), "contains must return false for absent element");
        check(!q.contains(null), "contains(null) must return false on non-empty queue");

        q.poll();
        check(!q.contains("a"), "contains must not find removed element");
        check(q.contains("b"), "contains must still find remaining element");

        q.clear();
        check(!q.contains("b"), "contains must return false after clear");

        q.add("x");
        check(q.contains(new EqualsProbe()), "contains must use o.equals(element) matching");

        LinkedBlockingQueue<String> bounded = new LinkedBlockingQueue<>(2);
        bounded.offer("x");
        check(bounded.contains("x"), "bounded queue must support contains");
        check(!bounded.contains("y"), "bounded queue must return false for absent element");

        List<String> source = new ArrayList<>();
        source.add("p");
        source.add("q");
        LinkedBlockingQueue<String> fromCollection = new LinkedBlockingQueue<>(source);
        check(fromCollection.contains("q"), "collection-constructed queue must contain copied element");
        check(!fromCollection.contains("z"), "collection-constructed queue must return false for absent element");
    }
}
