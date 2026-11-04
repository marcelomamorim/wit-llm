/*
 * @test
 * @summary Observable behavior of the no-arg WeakHashMap constructor
 * @run main WeakHashMapWitupTest
 */
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        WeakHashMap<String, String> m = new WeakHashMap<>();
        if (!m.isEmpty() || m.size() != 0) {
            throw new RuntimeException("new map is not empty");
        }
        if (m.get("a") != null) {
            throw new RuntimeException("get on empty map returned non-null");
        }
        if (m.containsKey("a")) {
            throw new RuntimeException("containsKey on empty map returned true");
        }
        if (m.put("a", "1") != null) {
            throw new RuntimeException("first put returned non-null previous value");
        }
        if (m.size() != 1 || !"1".equals(m.get("a"))) {
            throw new RuntimeException("mapping not stored correctly");
        }
        if (!"1".equals(m.put("a", "2"))) {
            throw new RuntimeException("replacing put did not return old value");
        }
        if (m.size() != 1) {
            throw new RuntimeException("replacing put changed size");
        }
        m.put(null, "n");
        if (!"n".equals(m.get(null))) {
            throw new RuntimeException("null key not supported");
        }
        m.put("b", null);
        if (!m.containsKey("b") || m.get("b") != null) {
            throw new RuntimeException("null value not supported");
        }
        if (m.size() != 3) {
            throw new RuntimeException("unexpected size: " + m.size());
        }
        if (!"n".equals(m.remove(null))) {
            throw new RuntimeException("remove of null key returned wrong value");
        }
        if (m.containsKey(null)) {
            throw new RuntimeException("null key still present after remove");
        }
        WeakHashMap<String, String> c = new WeakHashMap<>(16, 0.75f);
        if (!c.isEmpty() || c.size() != 0) {
            throw new RuntimeException("explicit-capacity map is not empty");
        }
    }
}
