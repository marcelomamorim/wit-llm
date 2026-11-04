/*
 * @test
 * @summary Tests for java.util.Properties class, focusing on replaceAll method.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.function.BiFunction;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
    }

    public static void testReplaceAllWithNullFunction() {
        Properties props = new Properties();
        BiFunction<Object, Object, Object> nullFunction = null;
        try {
            props.replaceAll(nullFunction);
            // If no exception, test fails
            System.err.println("Expected NullPointerException not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null function.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    public static void testReplaceAllWithValidFunction() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");
        BiFunction<Object, Object, Object> concatFunction = (k, v) -> v + "_updated";
        try {
            props.replaceAll(concatFunction);
            String val1 = props.getProperty("key1");
            String val2 = props.getProperty("key2");
            if ("value1_updated".equals(val1) && "value2_updated".equals(val2)) {
                System.out.println("replaceAll successfully updated properties.");
            } else {
                System.err.println("Properties not updated as expected.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception during replaceAll: " + e);
            System.exit(1);
        }
    }
}
