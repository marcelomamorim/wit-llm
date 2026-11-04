/*
 * @test
 * @summary Witup-derived checks for ConcurrentHashMap(int initialCapacity) constructor
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);

        ConcurrentHashMap<String, String> zero = new ConcurrentHashMap<>(0);
        check(zero.isEmpty(), "new map with capacity 0 should be empty");
        zero.put("a", "1");
        check("1".equals(zero.get("a")), "value lost after put on capacity 0 map");
        check(zero.size() == 1, "unexpected size after single put");

        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(16);
        for (int i = 0; i < 1000; i++) {
            map.put(i, i * 2);
        }
        check(map.size() == 1000, "unexpected size after 1000 puts");
        check(map.get(500) == 1000, "unexpected value for key 500");

        ConcurrentHashMap<Integer, String> large = new ConcurrentHashMap<>(1 << 20);
        large.put(7, "seven");
        check("seven".equals(large.get(7)), "value lost in large-capacity map");
    }

    private static void expectIllegalArgument(int capacity) {
        try {
            new ConcurrentHashMap<String, String>(capacity);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for capacity " + capacity);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
