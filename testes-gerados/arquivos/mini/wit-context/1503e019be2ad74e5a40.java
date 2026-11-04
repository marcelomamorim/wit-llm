/*
 * @test
 * @summary Test java.util.Properties.replaceAll method for null function and normal operation
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.function.BiFunction;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllNormalFunction();
    }

    private static void testReplaceAllNullFunction() {
        Properties props = new Properties();
        boolean npeThrown = false;
        try {
            props.replaceAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when function is null");
        }
    }

    private static void testReplaceAllNormalFunction() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");

        BiFunction<Object, Object, Object> toUpperCase = new BiFunction<Object, Object, Object>() {
            @Override
            public Object apply(Object k, Object v) {
                if (v instanceof String) {
                    return ((String) v).toUpperCase();
                }
                return v;
            }
        };

        props.replaceAll(toUpperCase);

        if (!"VALUE1".equals(props.getProperty("key1"))) {
            throw new AssertionError("Expected key1 value to be 'VALUE1' after replaceAll");
        }
        if (!"VALUE2".equals(props.getProperty("key2"))) {
            throw new AssertionError("Expected key2 value to be 'VALUE2' after replaceAll");
        }
    }
}
