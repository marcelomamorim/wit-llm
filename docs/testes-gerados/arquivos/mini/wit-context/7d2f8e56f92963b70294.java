/*
 * @test
 * @summary Test Collections.SingletonMap.replaceAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSingletonMapReplaceAllThrows();
    }

    private static void testSingletonMapReplaceAllThrows() {
        // Create a singleton map
        java.util.Map<String, String> map = Collections.singletonMap("key", "value");

        // The replaceAll method in SingletonMap is expected to throw UnsupportedOperationException
        try {
            map.replaceAll(new BiFunction<String, String, String>() {
                @Override
                public String apply(String k, String v) {
                    return v + "_modified";
                }
            });
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }
    }
}
