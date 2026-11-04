/*
 * @test
 * @summary Verify that compute on immutable maps throws UnsupportedOperationException without invoking the remapping function
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkCompute(Map.of());
        checkCompute(Map.of("a", 1));
        checkCompute(Map.of("a", 1, "b", 2));
        System.out.println("PASS");
    }

    static void checkCompute(Map<String, Integer> map) {
        final boolean[] invoked = {false};
        BiFunction<String, Integer, Integer> rf = (k, v) -> {
            invoked[0] = true;
            return v == null ? 1 : v + 1;
        };
        try {
            map.compute("a", rf);
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (invoked[0]) {
            throw new RuntimeException("remapping function was invoked");
        }
    }
}
