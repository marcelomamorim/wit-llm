/*
 * @test
 * @summary Verifies that adding to the Hashtable entry set throws UnsupportedOperationException and leaves the table unchanged
 * @run main HashtableWitupTest
 */

import java.util.AbstractMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class HashtableWitupTest {

    public static void main(String[] args) {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);

        Set<Map.Entry<String, Integer>> entries = table.entrySet();
        Map.Entry<String, Integer> newEntry = new AbstractMap.SimpleEntry<>("two", 2);

        boolean thrown = false;
        try {
            entries.add(newEntry);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from entrySet().add");
        }

        if (table.size() != 1) {
            throw new RuntimeException("Table size changed after failed add: " + table.size());
        }
        if (table.containsKey("two")) {
            throw new RuntimeException("Entry was inserted despite UnsupportedOperationException");
        }
        if (!Integer.valueOf(1).equals(table.get("one"))) {
            throw new RuntimeException("Existing mapping was altered");
        }
    }
}
