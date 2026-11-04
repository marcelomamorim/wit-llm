/*
 * @test
 * @summary Test Collections.CheckedMap constructor null checks
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNonNullParameters();
    }

    private static void testNonNullParameters() {
        Map<String, String> map = new HashMap<>();
        Class<String> keyClass = String.class;
        Class<String> valueClass = String.class;

        // Normal construction should succeed
        Collections.CheckedMap<String, String> checkedMap = new Collections.CheckedMap<>(map, keyClass, valueClass);
        if (checkedMap == null) throw new AssertionError("CheckedMap instance is null");

        // Test null map
        try {
            new Collections.CheckedMap<>(null, keyClass, valueClass);
            throw new AssertionError("Expected NullPointerException for null map");
        } catch (NullPointerException expected) {
        }

        // Test null keyType
        try {
            new Collections.CheckedMap<>(map, null, valueClass);
            throw new AssertionError("Expected NullPointerException for null keyType");
        } catch (NullPointerException expected) {
        }

        // Test null valueType
        try {
            new Collections.CheckedMap<>(map, keyClass, null);
            throw new AssertionError("Expected NullPointerException for null valueType");
        } catch (NullPointerException expected) {
        }
    }
}
