/*
 * @test
 * @summary Witup-generated tests for ConcurrentSkipListMap.remove(Object, Object)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        map.put(1, 10);
        map.put(2, 20);

        check(map.remove(1, 11) == false, "mismatched value must not remove");
        check(map.containsKey(1), "entry 1 must still be present");

        check(map.remove(3, 30) == false, "absent key must return false");

        check(map.remove(2, null) == false, "null value must return false");
        check(map.containsKey(2), "entry 2 must still be present after null value");

        check(map.remove(1, 10) == true, "matching pair must be removed");
        check(!map.containsKey(1), "entry 1 must be gone");
        check(map.size() == 1, "size must be 1 after removal");

        boolean thrown = false;
        try {
            map.remove(null, 20);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null key must throw NullPointerException");
        check(map.containsKey(2), "map must be unchanged after null key call");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
