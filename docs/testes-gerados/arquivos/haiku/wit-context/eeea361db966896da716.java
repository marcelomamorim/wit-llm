/*
 * @test
 * @summary Verifies that putIfAbsent on a singleton map from Collections.singletonMap throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentExistingKeyThrows();
        testPutIfAbsentNewKeyThrows();
    }

    static void testPutIfAbsentExistingKeyThrows() {
        Map<String, Integer> map = Collections.singletonMap("a", 1);
        boolean thrown = false;
        try {
            map.putIfAbsent("a", 2);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException for existing key");
        }
        if (map.get("a") != 1 || map.size() != 1) {
            throw new AssertionError("Map was modified after failed putIfAbsent");
        }
    }

    static void testPutIfAbsentNewKeyThrows() {
        Map<String, Integer> map = Collections.singletonMap("a", 1);
        boolean thrown = false;
        try {
            map.putIfAbsent("b", 2);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException for new key");
        }
        if (map.containsKey("b") || map.size() != 1) {
            throw new AssertionError("New key was added after failed putIfAbsent");
        }
    }
}
