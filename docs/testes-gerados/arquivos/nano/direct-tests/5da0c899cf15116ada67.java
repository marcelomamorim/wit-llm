/* @test    @summary Test TreeMap.subMap method for expected exception behavior and basic invocation    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testSubMapThrowsInternalError();
        testSubMapBasicInvocation();
    }

    private static void testSubMapThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.subMap(1, 10);
        } catch (InternalError e) {
            // Expected exception, test passes
            return;
        }
        throw new AssertionError("Expected InternalError was not thrown");
    }

    private static void testSubMapBasicInvocation() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        SortedMap<Integer, String> subMap = map.subMap(1, 3);
        if (subMap == null) {
            throw new AssertionError("subMap returned null");
        }
        if (subMap.size() != 2) {
            throw new AssertionError("subMap size expected 2, got " + subMap.size());
        }
        if (!subMap.containsKey(1) || !subMap.containsKey(2)) {
            throw new AssertionError("subMap does not contain expected keys");
        }
        if (subMap.containsKey(3)) {
            throw new AssertionError("subMap should not contain key 3");
        }
    }
}
