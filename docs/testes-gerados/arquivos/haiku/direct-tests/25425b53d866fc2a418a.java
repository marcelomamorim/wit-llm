/*
 * @test
 * @summary Verifies that TreeMap.headMap(K) behaves as the current source defines (throws InternalError)
 * @run main TreeMapWitupTest
 */

import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testHeadMapNonNullKeyThrowsInternalError() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean thrown = false;
        try {
            SortedMap<String, Integer> head = map.headMap("b");
            check(head != null, "headMap returned null");
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "headMap(K) should throw InternalError per current source");
    }

    static void testHeadMapNullKeyThrowsInternalError() {
        TreeMap<String, Integer> map = new TreeMap<>();
        boolean thrown = false;
        try {
            map.headMap(null);
        } catch (InternalError e) {
            thrown = true;
        }
        check(thrown, "headMap(null) should throw InternalError per current source");
    }

    public static void main(String[] args) {
        testHeadMapNonNullKeyThrowsInternalError();
        testHeadMapNullKeyThrowsInternalError();
    }
}
