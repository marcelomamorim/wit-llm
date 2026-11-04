/*
 * @test
 * @summary Verify that replace(K, V) on a Collections.singletonMap throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKeyThrows();
        testReplaceMissingKeyThrows();
        testReplaceDoesNotChangeMapState();
    }

    static void testReplaceExistingKeyThrows() {
        Map<String, Integer> m = Collections.singletonMap("a", 1);
        boolean thrown = false;
        try {
            m.replace("a", 2);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from replace on existing key");
        }
    }

    static void testReplaceMissingKeyThrows() {
        Map<String, Integer> m = Collections.singletonMap("a", 1);
        boolean thrown = false;
        try {
            m.replace("b", 3);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from replace on missing key");
        }
    }

    static void testReplaceDoesNotChangeMapState() {
        Map<String, Integer> m = Collections.singletonMap("a", 1);
        try {
            m.replace("a", 2);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (m.get("a") != 1 || m.size() != 1) {
            throw new RuntimeException("singletonMap state changed after failed replace");
        }
    }
}
