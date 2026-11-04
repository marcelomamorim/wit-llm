/* @test    @summary Test for Collections.CheckedMap constructor behavior    @run main CollectionsWitupTest */

import java.util.*;
import java.lang.reflect.*;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testCheckedMapConstructorWithValidArguments();
        testCheckedMapConstructorWithNullMap();
        testCheckedMapConstructorWithNullKeyType();
        testCheckedMapConstructorWithNullValueType();
    }

    static void testCheckedMapConstructorWithValidArguments() {
        Map<String, Integer> map = new HashMap<>();
        Class<String> keyType = String.class;
        Class<Integer> valueType = Integer.class;
        CheckedMap<String, Integer> checkedMap = new CheckedMap<>(map, keyType, valueType);
        assert checkedMap != null : "CheckedMap should not be null";
        assert checkedMap.m == map : "Underlying map should be the same";
        assert checkedMap.keyType == keyType : "Key type should match";
        assert checkedMap.valueType == valueType : "Value type should match";
    }

    static void testCheckedMapConstructorWithNullMap() {
        try {
            new CheckedMap<>(null, String.class, Integer.class);
            assert false : "Expected NullPointerException for null map";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testCheckedMapConstructorWithNullKeyType() {
        Map<String, Integer> map = new HashMap<>();
        try {
            new CheckedMap<>(map, null, Integer.class);
            assert false : "Expected NullPointerException for null keyType";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testCheckedMapConstructorWithNullValueType() {
        Map<String, Integer> map = new HashMap<>();
        try {
            new CheckedMap<>(map, String.class, null);
            assert false : "Expected NullPointerException for null valueType";
        } catch (NullPointerException e) {
            // expected
        }
    }

    // Inner class to access the package-private constructor
    static class CheckedMap<K, V> extends java.util.Collections.CheckedMap<K, V> {
        public CheckedMap(Map<K, V> m, Class<K> keyType, Class<V> valueType) {
            super(m, keyType, valueType);
        }
    }
}
