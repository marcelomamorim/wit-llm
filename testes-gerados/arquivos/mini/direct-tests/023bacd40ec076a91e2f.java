/*
 * @test
 * @summary Test java.util.Properties.forEach method with various BiConsumer actions
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.function.BiConsumer;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        testForEachWithEmptyProperties();
        testForEachWithOneEntry();
        testForEachWithMultipleEntries();
        testForEachWithNullAction();
    }

    private static void testForEachWithEmptyProperties() {
        Properties props = new Properties();
        final int[] count = {0};
        props.forEach(new BiConsumer<Object, Object>() {
            public void accept(Object k, Object v) {
                count[0]++;
            }
        });
        if (count[0] != 0) {
            throw new RuntimeException("forEach iterated over entries in empty Properties");
        }
    }

    private static void testForEachWithOneEntry() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        final int[] count = {0};
        final String[] key = new String[1];
        final String[] value = new String[1];
        props.forEach(new BiConsumer<Object, Object>() {
            public void accept(Object k, Object v) {
                count[0]++;
                key[0] = (String) k;
                value[0] = (String) v;
            }
        });
        if (count[0] != 1) {
            throw new RuntimeException("forEach did not iterate exactly once");
        }
        if (!"key1".equals(key[0]) || !"value1".equals(value[0])) {
            throw new RuntimeException("forEach iterated with wrong key or value");
        }
    }

    private static void testForEachWithMultipleEntries() {
        Properties props = new Properties();
        props.setProperty("k1", "v1");
        props.setProperty("k2", "v2");
        props.setProperty("k3", "v3");
        final java.util.Set<String> keys = new java.util.HashSet<>();
        final java.util.Set<String> values = new java.util.HashSet<>();
        props.forEach(new BiConsumer<Object, Object>() {
            public void accept(Object k, Object v) {
                keys.add((String) k);
                values.add((String) v);
            }
        });
        if (keys.size() != 3 || values.size() != 3) {
            throw new RuntimeException("forEach did not iterate over all entries");
        }
        if (!keys.contains("k1") || !keys.contains("k2") || !keys.contains("k3")) {
            throw new RuntimeException("forEach keys missing expected keys");
        }
        if (!values.contains("v1") || !values.contains("v2") || !values.contains("v3")) {
            throw new RuntimeException("forEach values missing expected values");
        }
    }

    private static void testForEachWithNullAction() {
        Properties props = new Properties();
        boolean threw = false;
        try {
            props.forEach(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("forEach did not throw NullPointerException when action is null");
        }
    }
}
