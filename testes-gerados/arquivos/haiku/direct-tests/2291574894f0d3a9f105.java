/*
 * @test
 * @summary Verifies observable behavior of ConcurrentSkipListMap.containsKey for natural ordering, custom comparator, copy constructor, and removal
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testPresenceAfterPut();
        testAbsentKey();
        testAfterRemove();
        testFalseValueStillContained();
        testComparatorEquivalence();
        testCopyConstructor();
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        check(!m.containsKey("a"), "empty map must not contain key");
    }

    static void testPresenceAfterPut() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        check(m.containsKey("a"), "key must be contained after put");
    }

    static void testAbsentKey() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        check(!m.containsKey("b"), "absent key must not be reported as contained");
    }

    static void testAfterRemove() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        m.remove("a");
        check(!m.containsKey("a"), "removed key must not be contained");
    }

    static void testFalseValueStillContained() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("z", 0);
        check(m.containsKey("z"), "containsKey must depend on mapping presence, not on value");
    }

    static void testComparatorEquivalence() {
        ConcurrentSkipListMap<String, String> ci =
                new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        ci.put("Key", "v");
        check(ci.containsKey("KEY"), "comparator-equivalent key must be contained");
        check(ci.containsKey("key"), "comparator-equivalent key must be contained (lowercase)");
        check(!ci.containsKey("other"), "unrelated key must not be contained");
    }

    static void testCopyConstructor() {
        Map<String, Integer> src = new HashMap<>();
        src.put("p", 10);
        src.put("q", 20);
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>(src);
        check(m.containsKey("p"), "copied key p must be contained");
        check(m.containsKey("q"), "copied key q must be contained");
        check(!m.containsKey("r"), "non-copied key r must not be contained");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
