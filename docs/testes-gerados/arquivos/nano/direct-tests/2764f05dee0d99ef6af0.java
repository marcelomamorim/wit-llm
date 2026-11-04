/* @test    @summary Test for Hashtable.put method to ensure it correctly adds and replaces entries.    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testPutNewEntry();
        testReplaceExistingEntry();
    }

    static void testPutNewEntry() {
        Hashtable<String, String> table = new Hashtable<>();
        String previous = table.put("key1", "value1");
        assert previous == null : "Expected null when inserting new key";
        String retrieved = table.get("key1");
        assert "value1".equals(retrieved) : "Value retrieved does not match inserted value";
    }

    static void testReplaceExistingEntry() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key2", "initial");
        String previous = table.put("key2", "updated");
        assert "initial".equals(previous) : "Expected previous value to be 'initial'";
        String retrieved = table.get("key2");
        assert "updated".equals(retrieved) : "Value after update does not match";
    }
}
