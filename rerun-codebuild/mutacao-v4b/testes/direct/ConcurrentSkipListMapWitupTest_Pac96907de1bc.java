/* @test
   @summary Testa o método replace de ConcurrentSkipListMap para comportamentos básicos e condições de exceção.
   @run main ConcurrentSkipListMapWitupTest_Pac96907de1bc
 */
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Objects;

public class ConcurrentSkipListMapWitupTest_Pac96907de1bc {
    public static void main(String[] args) {
        testReplaceBasic();
        testReplaceNullArguments();
        testReplaceNonExistingKey();
        testReplaceOldValueMismatch();
    }

    static void testReplaceBasic() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean result = map.replace("key1", "value1", "value2");
        assert result : "Replace should succeed when key exists with matching oldValue";
        assert "value2".equals(map.get("key1")) : "Value should be updated to newValue";
    }

    static void testReplaceNullArguments() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        try {
            map.replace(null, "value", "newValue");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) { /* expected */ }
        try {
            map.replace("key", null, "newValue");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) { /* expected */ }
        try {
            map.replace("key", "value", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) { /* expected */ }
    }

    static void testReplaceNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.replace("nonexistent", "value", "newValue");
        assert !result : "Replace should fail for non-existing key";
    }

    static void testReplaceOldValueMismatch() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key2", "value2");
        boolean result = map.replace("key2", "wrongOldValue", "newValue");
        assert !result : "Replace should fail when oldValue does not match";
        assert "value2".equals(map.get("key2")) : "Value should remain unchanged";
    }
}
