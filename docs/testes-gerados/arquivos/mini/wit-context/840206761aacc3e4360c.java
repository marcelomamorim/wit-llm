/*
 * @test
 * @summary Test ConcurrentSkipListMap.removeIf(Predicate) for null filter and basic removal
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullFilter();
        testRemoveIfBasic();
    }

    static void testRemoveIfNullFilter() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.removeIf(null);
            throw new RuntimeException("Expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfBasic() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "apple");
        map.put("b", "banana");
        map.put("c", "cherry");

        // Remove values starting with 'b'
        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String v) {
                return v.startsWith("b");
            }
        });

        if (!removed) {
            throw new RuntimeException("Expected removal to occur");
        }

        if (map.containsKey("b")) {
            throw new RuntimeException("Entry with key 'b' should have been removed");
        }

        if (!map.containsKey("a") || !map.containsKey("c")) {
            throw new RuntimeException("Entries with keys 'a' and 'c' should remain");
        }

        // Remove values starting with 'z' (none)
        boolean removedNone = map.removeIf(new Predicate<String>() {
            public boolean test(String v) {
                return v.startsWith("z");
            }
        });

        if (removedNone) {
            throw new RuntimeException("Expected no removal when predicate matches no values");
        }
    }
}
