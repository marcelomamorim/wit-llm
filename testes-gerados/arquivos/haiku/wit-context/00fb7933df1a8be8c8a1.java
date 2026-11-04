/*
 * @test
 * @summary Checks argument validation of ConcurrentHashMap(int initialCapacity, float loadFactor)
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        expectIllegalArgument(0, 0.0f);
        expectIllegalArgument(16, 0.0f);
        expectIllegalArgument(16, -0.5f);
        expectIllegalArgument(16, Float.NaN);
        expectIllegalArgument(-1, 0.75f);
        expectIllegalArgument(-16, 1.0f);

        ConcurrentHashMap<String, Integer> zeroCap = new ConcurrentHashMap<>(0, 0.75f);
        zeroCap.put("a", 1);
        if (zeroCap.get("a") != 1) {
            throw new RuntimeException("unexpected value for zero-capacity map");
        }

        ConcurrentHashMap<String, Integer> tiny = new ConcurrentHashMap<>(16, Float.MIN_VALUE);
        tiny.put("k", 42);
        if (tiny.get("k") != 42 || tiny.size() != 1) {
            throw new RuntimeException("unexpected state for tiny load factor map");
        }

        ConcurrentHashMap<String, Integer> big = new ConcurrentHashMap<>(1024, 2.0f);
        if (!big.isEmpty()) {
            throw new RuntimeException("new map should be empty");
        }
    }

    private static void expectIllegalArgument(int initialCapacity, float loadFactor) {
        try {
            new ConcurrentHashMap<String, Integer>(initialCapacity, loadFactor);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for capacity="
                + initialCapacity + ", loadFactor=" + loadFactor);
    }
}
