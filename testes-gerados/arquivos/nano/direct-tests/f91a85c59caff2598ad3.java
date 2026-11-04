/*
 * @test
 * @summary Test Collections.retainAll to ensure it throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        try {
            Collections.retainAll(list, Arrays.asList("a", "b"));
            // If no exception, fail the test
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes
        }
    }
}
