/*
 * @test
 * @summary Verifies IdentityHashMap(int expectedMaxSize) rejects negative sizes and accepts zero and positive sizes
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testNegativeThrowsIllegalArgument(-1);
        testNegativeThrowsIllegalArgument(Integer.MIN_VALUE);
        testZeroAndPositiveSizesWork();
    }

    static void testNegativeThrowsIllegalArgument(int expectedMaxSize) {
        try {
            new IdentityHashMap<String, String>(expectedMaxSize);
            throw new RuntimeException("No exception for expectedMaxSize=" + expectedMaxSize);
        } catch (IllegalArgumentException e) {
            String expectedMsg = "expectedMaxSize is negative: " + expectedMaxSize;
            if (!expectedMsg.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testZeroAndPositiveSizesWork() {
        int[] sizes = {0, 1, 16, 1000};
        for (int size : sizes) {
            IdentityHashMap<String, Integer> m = new IdentityHashMap<>(size);
            if (!m.isEmpty() || m.size() != 0) {
                throw new RuntimeException("Map not empty for size hint " + size);
            }
            String key = new String("k");
            m.put(key, 1);
            if (m.size() != 1) {
                throw new RuntimeException("Wrong size after put for size hint " + size);
            }
            if (!Integer.valueOf(1).equals(m.get(key))) {
                throw new RuntimeException("get with same key failed for size hint " + size);
            }
            if (m.get(new String("k")) != null) {
                throw new RuntimeException("equal but non-identical key was matched for size hint " + size);
            }
        }
    }
}
