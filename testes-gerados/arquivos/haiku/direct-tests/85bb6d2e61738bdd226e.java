/*
 * @test
 * @summary Verifies LinkedHashMap entry iterator next() returns entries in insertion and access order
 * @run main LinkedHashMapWitupTest
 */
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testInsertionOrder();
        testReinsertKeepsOrder();
        testAccessOrder();
        testNextThrowsWhenExhausted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String keysInIterationOrder(LinkedHashMap<String, Integer> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getKey());
        }
        return sb.toString();
    }

    static void testInsertionOrder() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("b", 2);
        m.put("a", 1);
        m.put("c", 3);
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        Map.Entry<String, Integer> e1 = it.next();
        check(e1.getKey().equals("b") && e1.getValue().intValue() == 2, "first entry wrong");
        Map.Entry<String, Integer> e2 = it.next();
        check(e2.getKey().equals("a") && e2.getValue().intValue() == 1, "second entry wrong");
        Map.Entry<String, Integer> e3 = it.next();
        check(e3.getKey().equals("c") && e3.getValue().intValue() == 3, "third entry wrong");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testReinsertKeepsOrder() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("b", 2);
        m.put("a", 1);
        m.put("c", 3);
        m.put("b", 5);
        check(keysInIterationOrder(m).equals("bac"), "reinsertion changed order: " + keysInIterationOrder(m));
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        Map.Entry<String, Integer> first = it.next();
        check(first.getValue().intValue() == 5, "updated value not visible through next()");
    }

    static void testAccessOrder() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>(16, 0.75f, true);
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        m.get("a");
        check(keysInIterationOrder(m).equals("bca"), "access order wrong: " + keysInIterationOrder(m));
    }

    static void testNextThrowsWhenExhausted() {
        Iterator<Map.Entry<String, Integer>> it = new LinkedHashMap<String, Integer>().entrySet().iterator();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
