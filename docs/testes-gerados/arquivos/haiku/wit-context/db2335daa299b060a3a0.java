/*
 * @test
 * @summary Verify TreeMap.comparator() and comparator propagation to public views and copies without InternalError
 * @run main TreeMapWitupTest
 */
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, Integer> natural = new TreeMap<>();
        check(natural.comparator() == null, "natural ordering comparator should be null");

        Comparator<String> cmp = Comparator.reverseOrder();
        TreeMap<String, Integer> m = new TreeMap<>(cmp);
        check(m.comparator() == cmp, "comparator should be the supplied instance");

        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);

        NavigableMap<String, Integer> head = m.headMap("b", false);
        check(head.comparator() == cmp, "headMap comparator should be the same instance");

        NavigableMap<String, Integer> tail = m.tailMap("b", true);
        check(tail.comparator() == cmp, "tailMap comparator should be the same instance");

        NavigableMap<String, Integer> sub = m.subMap("c", true, "a", true);
        check(sub.comparator() == cmp, "subMap comparator should be the same instance");

        check(m.descendingMap().comparator() != null, "descendingMap comparator should not be null");

        TreeMap<String, Integer> copy = new TreeMap<>(m);
        check(copy.comparator() == cmp, "SortedMap copy should preserve comparator");

        Map<String, Integer> plain = new TreeMap<>();
        plain.put("x", 1);
        TreeMap<String, Integer> fromMap = new TreeMap<>(plain);
        check(fromMap.comparator() == null, "Map copy of natural-order map should have null comparator");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
