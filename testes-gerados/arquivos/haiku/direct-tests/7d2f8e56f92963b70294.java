/*
 * @test
 * @summary Verifies that replaceAll on an unmodifiable map view from Collections throws UnsupportedOperationException without mutating the backing map
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsAndDoesNotMutate();
        testReplaceAllFunctionNotInvoked();
        testReplaceAllThrowsWithNullFunction();
    }

    static void testReplaceAllThrowsAndDoesNotMutate() {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        base.put("b", 2);
        Map<String, Integer> view = Collections.unmodifiableMap(base);

        boolean thrown = false;
        try {
            view.replaceAll((k, v) -> v + 10);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException");
        }
        if (base.get("a") != 1 || base.get("b") != 2) {
            throw new RuntimeException("Backing map was mutated");
        }
    }

    static void testReplaceAllFunctionNotInvoked() {
        Map<String, Integer> base = new HashMap<>();
        base.put("x", 5);
        Map<String, Integer> view = Collections.unmodifiableMap(base);

        final int[] calls = {0};
        try {
            view.replaceAll((k, v) -> {
                calls[0]++;
                return v;
            });
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (calls[0] != 0) {
            throw new RuntimeException("Function was invoked " + calls[0] + " times");
        }
    }

    static void testReplaceAllThrowsWithNullFunction() {
        Map<String, Integer> view = Collections.unmodifiableMap(new HashMap<String, Integer>());
        boolean thrown = false;
        try {
            view.replaceAll(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for null function");
        }
    }
}
