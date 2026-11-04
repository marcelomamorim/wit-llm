/*
 * @test
 * @summary Test java.util.Properties.replaceAll with BiFunction
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;
import java.util.function.BiFunction;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");

        // Replace all values by appending "_modified"
        props.replaceAll(new BiFunction<Object, Object, Object>() {
            @Override
            public Object apply(Object k, Object v) {
                if (k instanceof String && v instanceof String) {
                    return ((String) v) + "_modified";
                }
                return v;
            }
        });

        // Check that values were replaced correctly
        String v1 = props.getProperty("key1");
        String v2 = props.getProperty("key2");

        if (!"value1_modified".equals(v1)) {
            throw new AssertionError("Expected value1_modified but got " + v1);
        }
        if (!"value2_modified".equals(v2)) {
            throw new AssertionError("Expected value2_modified but got " + v2);
        }

        // Test replaceAll with a function that returns null (removes mapping)
        props.replaceAll(new BiFunction<Object, Object, Object>() {
            @Override
            public Object apply(Object k, Object v) {
                return null; // remove all entries
            }
        });

        if (!props.isEmpty()) {
            throw new AssertionError("Expected properties to be empty after replaceAll returning null");
        }
    }
}
