/*
 * @test    @summary Test for ConcurrentHashMap.replace(K key, V value) handling null inputs
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullValue();
        testReplaceWithValidInputs();
    }

    private static void testReplaceWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threwException = false;
        try {
            map.replace(null, "value");
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when key is null");
        }
    }

    private static void testReplaceWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threwException = false;
        try {
            map.replace("key", null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when value is null");
        }
    }

    private static void testReplaceWithValidInputs() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("existing", "oldValue");
        String previous = map.replace("existing", "newValue");
        if (previous != null) {
            throw new AssertionError("Expected previous value to be null for new key");
        }
        String replaced = map.replace("existing", "replacedValue");
        if (!"oldValue".equals(replaced)) {
            throw new AssertionError("Expected replaced value to be 'oldValue'");
        }
        String current = map.get("existing");
        if (!"replacedValue".equals(current)) {
            throw new AssertionError("Expected current value to be 'replacedValue'");
        }
    }
}
