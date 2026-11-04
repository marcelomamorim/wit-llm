/*
 * @test
 * @summary Verifies that replaceAll on an unmodifiable map view throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        base.put("b", 2);
        Map<String, Integer> view = Collections.unmodifiableMap(base);

        BiFunction<String, Integer, Integer> inc = (k, v) -> v + 1;
        try {
            view.replaceAll(inc);
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            view.replaceAll(null);
            throw new AssertionError("expected UnsupportedOperationException for null function");
        } catch (UnsupportedOperationException expected) {
        }

        if (base.get("a") != 1 || base.get("b") != 2 || base.size() != 2) {
            throw new AssertionError("backing map was modified");
        }
        if (view.get("a") != 1 || view.get("b") != 2) {
            throw new AssertionError("unmodifiable view values changed");
        }
    }
}
