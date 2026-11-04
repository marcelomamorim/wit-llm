/*
 * @test
 * @summary Test java.util.Collections.forEach method for null action argument
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
    }

    private static void testForEachNullAction() {
        Collections.EmptyMap<Object, Object> emptyMap = (Collections.EmptyMap<Object, Object>) Collections.emptyMap();
        try {
            emptyMap.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
