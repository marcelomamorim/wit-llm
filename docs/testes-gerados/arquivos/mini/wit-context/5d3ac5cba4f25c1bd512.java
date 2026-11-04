/*
 * @test
 * @summary Test Collections.UnmodifiableList.replaceAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable list
        List<String> list = Collections.unmodifiableList(java.util.Arrays.asList("a", "b", "c"));

        // Try to call replaceAll on the unmodifiable list and expect UnsupportedOperationException
        try {
            list.replaceAll(new UnaryOperator<String>() {
                public String apply(String s) {
                    return s.toUpperCase();
                }
            });
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Also test that the original list remains unchanged
        if (!list.get(0).equals("a") || !list.get(1).equals("b") || !list.get(2).equals("c")) {
            throw new RuntimeException("UnmodifiableList contents changed unexpectedly");
        }
    }
}
