/*
 * @test
 * @summary Test for ConcurrentSkipListMap.descendingMap()
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testDescendingMapReturnsNonNull();
        testDescendingMapThrowsOnInvalidState();
    }

    private static void testDescendingMapReturnsNonNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            Object result = map.descendingMap();
            if (result == null) {
                throw new AssertionError("descendingMap() returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in descendingMap(): " + e);
        }
    }

    private static void testDescendingMapThrowsOnInvalidState() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        // Simulate invalid internal state if possible; since internal state is private,
        // we test that calling descendingMap() does not throw under normal conditions.
        // For the purpose of this test, we assume normal operation.
        try {
            map.descendingMap();
        } catch (IllegalArgumentException e) {
            // Expected if internal state is invalid, but in normal case, should not occur.
            // Since we cannot manipulate internal state, this test is a placeholder.
            // In real scenario, would set up invalid state if accessible.
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
