/* @test    @summary Test for Properties.replaceAll method to ensure it applies the function correctly and handles nulls.    @run main PropertiesWitupTest */

import java.util.Properties;
import java.util.function.BiFunction;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithNullValues();
    }

    static void testReplaceAllWithValidFunction() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");
        
        BiFunction<Object, Object, Object> func = (k, v) -> v + "_modified";
        props.replaceAll(func);
        assert "value1_modified".equals(props.getProperty("key1")) : "key1 not modified";
        assert "value2_modified".equals(props.getProperty("key2")) : "key2 not modified";
    }

    static void testReplaceAllWithNullFunction() {
        Properties props = new Properties();
        props.setProperty("key", "value");
        try {
            props.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceAllWithNullValues() {
        Properties props = new Properties();
        props.setProperty("nullKey", null);
        props.setProperty("nullValue", "value");
        BiFunction<Object, Object, Object> func = (k, v) -> {
            if (v == null) return "null_value";
            return v;
        };
        props.replaceAll(func);
        assert "null_value".equals(props.getProperty("nullKey")) : "nullKey not replaced";
        assert "value".equals(props.getProperty("nullValue")) : "nullValue changed";
    }
}
