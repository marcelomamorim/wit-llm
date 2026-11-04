/*
 * @test
 * @summary Verifies that Hashtable entry set add(Map.Entry) is unsupported and leaves the table unchanged
 * @run main HashtableWitupTest
 */

import java.util.AbstractMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class HashtableWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void testEntrySetAddThrowsAndLeavesTableUnchanged() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        Set<Map.Entry<String, Integer>> entries = table.entrySet();

        boolean thrown = false;
        try {
            entries.add(new AbstractMap.SimpleEntry<>("two", 2));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "entrySet().add(entry) should throw UnsupportedOperationException");
        check(table.size() == 1, "table size must remain 1 after failed add");
        check(!table.containsKey("two"), "table must not contain the rejected key");
        check(table.get("one") == 1, "existing mapping must be preserved");
    }

    private static void testEntrySetAddOfExistingEntryThrows() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("k", 7);
        Set<Map.Entry<String, Integer>> entries = table.entrySet();

        boolean thrown = false;
        try {
            entries.add(new AbstractMap.SimpleEntry<>("k", 7));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "adding an already-present entry should still throw UnsupportedOperationException");
        check(table.size() == 1, "table size must remain 1");
        check(table.get("k") == 7, "value must remain unchanged");
    }

    public static void main(String[] args) {
        testEntrySetAddThrowsAndLeavesTableUnchanged();
        testEntrySetAddOfExistingEntryThrows();
    }
}
