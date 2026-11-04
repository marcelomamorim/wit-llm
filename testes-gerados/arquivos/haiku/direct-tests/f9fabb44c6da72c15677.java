/*
 * @test
 * @summary LinkedBlockingQueue.contains returns false for null, true for present elements (equals-based), and false for absent or removed elements
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

    static final class Key {
        final int v;
        Key(int v) { this.v = v; }
        @Override
        public boolean equals(Object o) {
            return (o instanceof Key) && ((Key) o).v == v;
        }
        @Override
        public int hashCode() { return v; }
    }

    public static void main(String[] args) {
        LinkedBlockingQueue<String> empty = new LinkedBlockingQueue<>();
        check(!empty.contains(null), "null must not be contained in empty queue");
        check(!empty.contains("a"), "empty queue must not contain \"a\"");

        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        check(q.contains("a"), "queue must contain \"a\"");
        check(q.contains("b"), "queue must contain \"b\"");
        check(!q.contains("c"), "queue must not contain \"c\"");
        check(!q.contains(null), "null must not be contained");

        q.poll();
        check(!q.contains("a"), "removed element must not be contained");
        check(q.contains("b"), "remaining element must still be contained");

        LinkedBlockingQueue<Key> keys = new LinkedBlockingQueue<>();
        keys.add(new Key(1));
        check(keys.contains(new Key(1)), "contains must use equals()");
        check(!keys.contains(new Key(2)), "non-equal key must not be contained");

        LinkedBlockingQueue<String> bounded = new LinkedBlockingQueue<>(1);
        bounded.add("x");
        check(bounded.contains("x"), "bounded queue must contain its element");
        check(!bounded.contains("y"), "bounded queue must not contain absent element");

        List<String> init = new ArrayList<>();
        init.add("p");
        init.add("q");
        LinkedBlockingQueue<String> fromCollection = new LinkedBlockingQueue<>(init);
        check(fromCollection.contains("p"), "collection-constructed queue must contain \"p\"");
        check(fromCollection.contains("q"), "collection-constructed queue must contain \"q\"");
        check(!fromCollection.contains("r"), "collection-constructed queue must not contain \"r\"");
    }
}
