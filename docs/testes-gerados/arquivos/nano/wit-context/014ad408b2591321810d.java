/*
 * @test
 * @summary Tests for java.util.Collections class, focusing on CheckedMap constructor behavior.
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testCheckedMapNullMap();
        testCheckedMapNullKeyType();
        testCheckedMapNullValueType();
    }

    public static void testCheckedMapNullMap() {
        try {
            new Collections.CheckedMap<>(null, String.class, Integer.class);
            throw new AssertionError("Expected NullPointerException for null map");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    public static void testCheckedMapNullKeyType() {
        Map<String, Integer> map = new HashMap<>();
        try {
            new Collections.CheckedMap<>(map, null, Integer.class);
            throw new AssertionError("Expected NullPointerException for null keyType");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    public static void testCheckedMapNullValueType() {
        Map<String, Integer> map = new HashMap<>();
        try {
            new Collections.CheckedMap<>(map, String.class, null);
            throw new AssertionError("Expected NullPointerException for null valueType");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
