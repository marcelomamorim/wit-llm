/*
 * @test
 * @summary Verifies that forEach on the map returned by Collections.emptyMap() rejects a null action with NullPointerException and invokes nothing for a non-null action
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testNonNullActionOnEmptyMapInvokesNothing();
    }

    static void testNullActionThrowsNullPointerException() {
        Map<String, Integer> map = Collections.emptyMap();
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testNonNullActionOnEmptyMapInvokesNothing() {
        Map<String, Integer> map = Collections.emptyMap();
        int[] calls = {0};
        map.forEach((k, v) -> calls[0]++);
        if (calls[0] != 0) {
            throw new RuntimeException("Action should not be invoked on empty map, calls=" + calls[0]);
        }
    }
}
