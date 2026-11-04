/*
 * @test
 * @summary Basic observable behavior of Collections.synchronizedMap
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsNewWrapper() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> wrapper = Collections.synchronizedMap(backing);
        check(wrapper != null, "wrapper must not be null");
        check(wrapper != backing, "wrapper must be a distinct object");
    }

    static void testPutIsVisibleInBackingMap() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> wrapper = Collections.synchronizedMap(backing);
        wrapper.put("a", 1);
        check(backing.get("a") == 1, "put via wrapper must reach backing map");
        check(wrapper.size() == 1, "wrapper size must reflect insertion");
    }

    static void testBackingChangesVisibleThroughWrapper() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> wrapper = Collections.synchronizedMap(backing);
        backing.put("b", 2);
        check(wrapper.get("b") == 2, "wrapper must observe backing map changes");
        check(wrapper.containsKey("b"), "containsKey must delegate");
    }

    static void testRemoveDelegates() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("c", 3);
        Map<String, Integer> wrapper = Collections.synchronizedMap(backing);
        Integer removed = wrapper.remove("c");
        check(removed == 3, "remove must return previous value");
        check(!backing.containsKey("c"), "remove must affect backing map");
    }

    static void testEqualsAgainstEquivalentMap() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("d", 4);
        Map<String, Integer> wrapper = Collections.synchronizedMap(backing);
        Map<String, Integer> other = new HashMap<>();
        other.put("d", 4);
        check(wrapper.equals(other), "wrapper must equal map with same mappings");
        check(other.equals(wrapper), "equality must be symmetric for equal mappings");
    }

    static void testNullKeyFollowsBackingMap() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> wrapper = Collections.synchronizedMap(backing);
        wrapper.put(null, 7);
        check(backing.containsKey(null), "null key must be stored by backing HashMap");
        check(wrapper.get(null) == 7, "null key must be retrievable through wrapper");
    }

    static void testDoubleWrappingIsConsistent() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> once = Collections.synchronizedMap(backing);
        Map<String, Integer> twice = Collections.synchronizedMap(once);
        twice.put("e", 5);
        check(backing.get("e") == 5, "double-wrapped put must reach original map");
        check(once.get("e") == 5, "inner wrapper must observe change");
    }

    static void testNullArgumentThrowsNpe() {
        try {
            Collections.synchronizedMap(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("synchronizedMap(null) must throw NullPointerException");
    }

    public static void main(String[] args) {
        testReturnsNewWrapper();
        testPutIsVisibleInBackingMap();
        testBackingChangesVisibleThroughWrapper();
        testRemoveDelegates();
        testEqualsAgainstEquivalentMap();
        testNullKeyFollowsBackingMap();
        testDoubleWrappingIsConsistent();
        testNullArgumentThrowsNpe();
    }
}
