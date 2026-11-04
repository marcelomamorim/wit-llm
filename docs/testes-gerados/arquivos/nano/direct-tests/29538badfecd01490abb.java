/* @test    @summary Test tryAdvance method of WeakHashMap for correct iteration behavior    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithEntries();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceNullAction();
    }

    static void testTryAdvanceWithEntries() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        final boolean[] called = {false};
        boolean result = map.tryAdvance(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("key1")) {
                assert value.equals("value1") : "Value mismatch for key1";
            } else if (key.equals("key2")) {
                assert value.equals("value2") : "Value mismatch for key2";
            } else {
                throw new AssertionError("Unexpected key: " + key);
            }
            called[0] = true;
        });
        assert result : "tryAdvance should return true when entries are present";
        assert called[0] : "Consumer should be called for each entry";
    }

    static void testTryAdvanceWithEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        final boolean[] called = {false};
        boolean result = map.tryAdvance(entry -> {
            called[0] = true;
        });
        assert !result : "tryAdvance should return false when map is empty";
        assert !called[0] : "Consumer should not be called when map is empty";
    }

    static void testTryAdvanceNullAction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        try {
            map.tryAdvance(null);
            assert false : "tryAdvance with null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
