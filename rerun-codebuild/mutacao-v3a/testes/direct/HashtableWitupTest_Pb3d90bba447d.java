/*
 * @test
 * @summary Tests the put method of java.util.Hashtable
 * @run main HashtableWitupTest_Pb3d90bba447d
 */

import java.util.Hashtable;

public class HashtableWitupTest_Pb3d90bba447d {

    public static void main(String[] args) {
        testPutWithNewKey();
        testPutWithExistingKey();
        testPutNullValue();
    }

    static void testPutWithNewKey() {
        Hashtable<String, String> table = new Hashtable<>();
        String previous = table.put("key1", "value1");
        assert previous == null : "Expected null for new key";
        assert "value1".equals(table.get("key1")) : "Value not stored correctly";
    }

    static void testPutWithExistingKey() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        String oldValue = table.put("key1", "value2");
        assert "value1".equals(oldValue) : "Expected previous value to be value1";
        assert "value2".equals(table.get("key1")) : "Value not updated correctly";
    }

    static void testPutNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        try {
            table.put("key", null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
