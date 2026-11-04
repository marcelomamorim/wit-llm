/*
 * @test
 * @summary Verify ConcurrentSkipListMap.containsKey observable behavior, including NullPointerException for a null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();

        check(!map.containsKey("a"), "empty map must not contain key");

        map.put("a", 1);
        check(map.containsKey("a"), "existing key must be found");
        check(!map.containsKey("b"), "absent key must not be found");

        map.remove("a");
        check(!map.containsKey("a"), "removed key must not be found");

        boolean thrown = false;
        try {
            map.containsKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "containsKey(null) must throw NullPointerException");

        ConcurrentSkipListMap<String, Integer> ci =
                new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        ci.put("Key", 1);
        check(ci.containsKey("KEY"), "comparator-based lookup must match case-insensitively");
        check(!ci.containsKey("other"), "comparator-based lookup must not match absent key");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
