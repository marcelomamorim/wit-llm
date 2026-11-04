/*
 * @test
 * @summary Verifies Hashtable entry setValue returns the old value, writes through to the table, and rejects null
 * @run main HashtableWitupTest
 */
import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {
    public static void main(String[] args) {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        Map.Entry<String, Integer> e = h.entrySet().iterator().next();

        Integer old = e.setValue(5);
        check(old != null && old.intValue() == 1, "setValue should return old value 1");
        check(h.get("a").intValue() == 5, "setValue should write through to the table");
        check(e.getValue().intValue() == 5, "entry should reflect new value");

        boolean npe = false;
        try {
            e.setValue(null);
        } catch (NullPointerException ex) {
            npe = true;
        }
        check(npe, "setValue(null) should throw NullPointerException");
        check(h.get("a").intValue() == 5, "value must be unchanged after rejected null");
        check(e.getValue().intValue() == 5, "entry value must be unchanged after rejected null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
