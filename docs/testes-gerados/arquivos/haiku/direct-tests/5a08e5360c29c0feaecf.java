/*
 * @test
 * @summary Verify that replaceAll on an unmodifiable map throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNonEmptyUnmodifiableMapThrows();
        testEmptyUnmodifiableMapThrows();
        testFunctionNotInvoked();
    }

    static void testNonEmptyUnmodifiableMapThrows() {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        base.put("b", 2);
        Map<String, Integer> um = Collections.unmodifiableMap(base);
        BiFunction<String, Integer, Integer> f = (k, v) -> v + 10;
        boolean thrown = false;
        try {
            um.replaceAll(f);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException");
        }
        if (base.get("a") != 1 || base.get("b") != 2) {
            throw new AssertionError("Backing map was modified");
        }
    }

    static void testEmptyUnmodifiableMapThrows() {
        Map<String, Integer> um = Collections.unmodifiableMap(new HashMap<String, Integer>());
        BiFunction<String, Integer, Integer> f = (k, v) -> v;
        boolean thrown = false;
        try {
            um.replaceAll(f);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException on empty map");
        }
    }

    static void testFunctionNotInvoked() {
        Map<String, Integer> base = new HashMap<>();
        base.put("x", 5);
        Map<String, Integer> um = Collections.unmodifiableMap(base);
        final int[] calls = {0};
        BiFunction<String, Integer, Integer> f = (k, v) -> {
            calls[0]++;
            return v;
        };
        try {
            um.replaceAll(f);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (calls[0] != 0) {
            throw new AssertionError("Function should not be applied, calls=" + calls[0]);
        }
    }
}
