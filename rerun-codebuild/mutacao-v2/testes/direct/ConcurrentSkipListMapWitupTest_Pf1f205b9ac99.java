/*
 * @test
 * @summary Test ConcurrentSkipListMap.putIfAbsent behavior including null value rejection
 * @run main ConcurrentSkipListMapWitupTest_Pf1f205b9ac99
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pf1f205b9ac99 {
    public static void main(String[] args) {
        testPutIfAbsentBasic();
        testPutIfAbsentNullValue();
    }

    private static void testPutIfAbsentBasic() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String prev = map.putIfAbsent("key1", "value1");
        if (prev != null) throw new AssertionError("Expected null on first putIfAbsent");
        String val = map.putIfAbsent("key1", "value2");
        if (!"value1".equals(val)) throw new AssertionError("Expected original value to be returned on putIfAbsent");
        if (!"value1".equals(map.get("key1"))) throw new AssertionError("Value should not be replaced by putIfAbsent");
    }

    private static void testPutIfAbsentNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
    }
}
