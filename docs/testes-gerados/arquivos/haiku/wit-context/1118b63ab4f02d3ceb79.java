/*
 * @test
 * @summary Verify Hashtable entry setValue returns the old value, writes through to the table, and rejects null with NullPointerException
 * @run main HashtableWitupTest
 */
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class HashtableWitupTest {
    public static void main(String[] args) {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        Iterator<Map.Entry<String, Integer>> it = h.entrySet().iterator();
        Map.Entry<String, Integer> e = it.next();

        Integer old = e.setValue(2);
        if (old == null || old.intValue() != 1) {
            throw new RuntimeException("setValue must return the previous value 1, got " + old);
        }
        if (h.get("a") == null || h.get("a").intValue() != 2) {
            throw new RuntimeException("setValue must write through to the table, got " + h.get("a"));
        }

        boolean thrown = false;
        try {
            e.setValue(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue(null) must throw NullPointerException");
        }
        if (h.get("a") == null || h.get("a").intValue() != 2) {
            throw new RuntimeException("value must remain 2 after rejected null setValue, got " + h.get("a"));
        }
    }
}
