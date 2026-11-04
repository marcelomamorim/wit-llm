/*
 * @test
 * @summary Tests for ConcurrentSkipListMap.replace(K, V)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullValue();
        testReplaceExistingKey();
        testReplaceNonExistingKey();
    }

    private static void testReplaceWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.replace(null, "value");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    private static void testReplaceWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "initial");
        boolean exceptionThrown = false;
        try {
            map.replace("key", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null value");
        }
    }

    private static void testReplaceExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("existing", "oldValue");
        String previousValue = map.replace("existing", "newValue");
        if (previousValue == null || !previousValue.equals("oldValue")) {
            throw new AssertionError("Replace did not return previous value as expected");
        }
        String currentValue = map.get("existing");
        if (!"newValue".equals(currentValue)) {
            throw new AssertionError("Value not updated correctly after replace");
        }
    }

    private static void testReplaceNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String result = map.replace("nonExisting", "value");
        if (result != null) {
            throw new AssertionError("Replace on non-existing key should return null");
        }
    }
}
