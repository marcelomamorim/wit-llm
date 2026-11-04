/*
 * @test
 * @summary Verifies ConcurrentSkipListMap.replace(K, V, V) null checks and observable compare-and-set behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    interface Action {
        void run() throws Exception;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(Action action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException(message + ": unexpected " + e, e);
        }
        throw new RuntimeException(message + ": no exception");
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");

        expectNpe(() -> map.replace(null, "1", "2"), "null key");
        expectNpe(() -> map.replace("a", null, "2"), "null oldValue");
        expectNpe(() -> map.replace("a", "1", null), "null newValue");
        expectNpe(() -> map.replace("missing", null, "2"), "null oldValue with absent key");
        check("1".equals(map.get("a")), "value changed after NPE");

        check(!map.replace("missing", "1", "2"), "absent key must return false");
        check(!map.containsKey("missing"), "absent key must not be inserted");

        check(!map.replace("a", "x", "2"), "mismatched oldValue must return false");
        check("1".equals(map.get("a")), "mismatched oldValue changed value");

        check(map.replace("a", "1", "2"), "matching oldValue must return true");
        check("2".equals(map.get("a")), "matching replace did not update value");

        check(!map.replace("a", "1", "3"), "stale oldValue must return false");
        check("2".equals(map.get("a")), "stale replace changed value");
    }
}
