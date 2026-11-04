/* @test    @summary Test TreeMap.replace method for correctness and regressions    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Map;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKeyWithMatchingOldValue();
        testReplaceExistingKeyWithNonMatchingOldValue();
        testReplaceNonExistingKey();
    }

    static void testReplaceExistingKeyWithMatchingOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        boolean replaced = map.replace("a", "1", "2");
        assert replaced : "Expected replace to return true when key exists with matching oldValue";
        assert "2".equals(map.get("a")) : "Value should be updated to '2'";
    }

    static void testReplaceExistingKeyWithNonMatchingOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("b", "1");
        boolean replaced = map.replace("b", "2", "3");
        assert !replaced : "Expected replace to return false when oldValue does not match";
        assert "1".equals(map.get("b")) : "Value should remain '1'";
    }

    static void testReplaceNonExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        boolean replaced = map.replace("c", "1", "2");
        assert !replaced : "Expected replace to return false when key does not exist";
        assert map.get("c") == null : "Value for non-existing key should be null";
    }
}
