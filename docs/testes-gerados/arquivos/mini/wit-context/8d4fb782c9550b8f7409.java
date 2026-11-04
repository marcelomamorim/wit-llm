/*
 * @test
 * @summary Test java.util.Collections.EmptyMap.replaceAll method for null function argument
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.BiFunction;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
    }

    private static void testReplaceAllNullFunction() {
        Collections.EmptyMap<Object, Object> emptyMap = (Collections.EmptyMap<Object, Object>) Collections.emptyMap();
        try {
            emptyMap.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
