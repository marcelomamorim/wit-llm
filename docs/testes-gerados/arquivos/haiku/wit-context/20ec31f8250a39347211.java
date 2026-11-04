/*
 * @test
 * @summary Tests observable Hashtable.containsValue behavior, including NullPointerException for null value
 * @run main HashtableWitupTest
 */
import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        Hashtable<String, Integer> table = new Hashtable<>();
        if (table.containsValue(1)) {
            throw new RuntimeException("empty table must not contain value 1");
        }
        table.put("one", 1);
        table.put("two", 2);
        if (!table.containsValue(1) || !table.containsValue(2)) {
            throw new RuntimeException("stored values must be found");
        }
        if (table.containsValue(3)) {
            throw new RuntimeException("absent value must not be found");
        }
        table.remove("one");
        if (table.containsValue(1)) {
            throw new RuntimeException("removed value must not be found");
        }
        boolean npeThrown = false;
        try {
            table.containsValue(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("containsValue(null) must throw NullPointerException");
        }
    }
}
