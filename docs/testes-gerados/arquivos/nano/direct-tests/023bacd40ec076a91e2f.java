/*
 * @test
 * @summary Tests the forEach method of java.util.Properties for correct behavior.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.function.BiConsumer;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyProperties();
        testForEachWithNullValues();
    }

    static void testForEachWithEntries() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");
        StringBuilder sb = new StringBuilder();
        BiConsumer<Object, Object> consumer = (k, v) -> {
            sb.append(k).append('=').append(v).append(';');
        };
        props.forEach(consumer);
        String result = sb.toString();
        boolean condition1 = result.contains("key1=value1;");
        boolean condition2 = result.contains("key2=value2;");
        if (!condition1 || !condition2) {
            throw new AssertionError("forEach did not process all entries correctly: " + result);
        }
    }

    static void testForEachWithEmptyProperties() {
        Properties props = new Properties();
        StringBuilder sb = new StringBuilder();
        BiConsumer<Object, Object> consumer = (k, v) -> {
            sb.append(k).append('=').append(v).append(';');
        };
        props.forEach(consumer);
        if (sb.length() != 0) {
            throw new AssertionError("forEach on empty properties should not process any entries.");
        }
    }

    static void testForEachWithNullValues() {
        Properties props = new Properties();
        props.put("nullKey", null);
        props.put("nullValue", null);
        StringBuilder sb = new StringBuilder();
        BiConsumer<Object, Object> consumer = (k, v) -> {
            sb.append(k).append('=').append(v).append(';');
        };
        props.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("nullKey=null") || !result.contains("nullValue=null")) {
            throw new AssertionError("forEach did not process null values correctly: " + result);
        }
    }
}
