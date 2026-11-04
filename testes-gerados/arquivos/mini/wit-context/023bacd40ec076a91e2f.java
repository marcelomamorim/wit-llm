/*
 * @test
 * @summary Test java.util.Properties.forEach method for null action and normal iteration
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;
import java.util.function.BiConsumer;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        try {
            props.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachWithValidAction() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        BiConsumer<Object, Object> action = new BiConsumer<Object, Object>() {
            @Override
            public void accept(Object k, Object v) {
                sb.append(k).append('=').append(v).append(';');
            }
        };

        props.forEach(action);

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new RuntimeException("forEach did not iterate all entries: " + result);
        }
    }
}
