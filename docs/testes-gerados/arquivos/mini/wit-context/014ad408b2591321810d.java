/*
 * @test
 * @summary Test java.util.Collections.CheckedMap constructor null argument checks
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullMap();
        testNullKeyType();
        testNullValueType();
        testValidConstruction();
    }

    static void testNullMap() {
        try {
            new Collections.CheckedMap(null, String.class, String.class);
            throw new RuntimeException("Expected NullPointerException for null map");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullKeyType() {
        Map<String, String> map = new HashMap<>();
        try {
            new Collections.CheckedMap(map, null, String.class);
            throw new RuntimeException("Expected NullPointerException for null keyType");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullValueType() {
        Map<String, String> map = new HashMap<>();
        try {
            new Collections.CheckedMap(map, String.class, null);
            throw new RuntimeException("Expected NullPointerException for null valueType");
        } catch (NullPointerException expected) {
        }
    }

    static void testValidConstruction() {
        Map<String, String> map = new HashMap<>();
        Collections.CheckedMap<String, String> checkedMap = new Collections.CheckedMap<>(map, String.class, String.class);
        if (checkedMap == null) {
            throw new RuntimeException("CheckedMap construction returned null");
        }
    }
}
