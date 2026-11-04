/*
 * @test
 * @summary Public-API checks for TreeMap entry spliterator forEachRemaining (null action, ordering, exhaustion)
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testTraversalOrder();
        testEmptyMapNoAction();
        testExhaustedAfterForEachRemaining();
    }

    static void testNullActionThrowsNPE() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        Spliterator<Map.Entry<String, Integer>> s = m.entrySet().spliterator();
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null action");
    }

    static void testTraversalOrder() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("c", 3);
        m.put("a", 1);
        m.put("b", 2);
        List<String> keys = new ArrayList<>();
        m.entrySet().spliterator().forEachRemaining(e -> keys.add(e.getKey()));
        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        if (!keys.equals(expected)) {
            throw new AssertionError("unexpected order: " + keys);
        }
    }

    static void testEmptyMapNoAction() {
        TreeMap<String, Integer> m = new TreeMap<>();
        List<String> calls = new ArrayList<>();
        m.entrySet().spliterator().forEachRemaining(e -> calls.add(e.getKey()));
        if (!calls.isEmpty()) {
            throw new AssertionError("action invoked on empty map: " + calls);
        }
    }

    static void testExhaustedAfterForEachRemaining() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("x", 10);
        m.put("y", 20);
        Spliterator<Map.Entry<String, Integer>> s = m.entrySet().spliterator();
        List<String> calls = new ArrayList<>();
        s.forEachRemaining(e -> calls.add(e.getKey()));
        if (calls.size() != 2) {
            throw new AssertionError("expected 2 entries, got " + calls);
        }
        boolean advanced = s.tryAdvance(e -> calls.add("unexpected"));
        if (advanced || calls.size() != 2) {
            throw new AssertionError("spliterator not exhausted after forEachRemaining");
        }
    }
}
