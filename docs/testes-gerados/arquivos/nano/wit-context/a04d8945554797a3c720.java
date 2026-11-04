/* @test    @summary Test for Properties.containsValue(Object value) method.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExisting();
        testContainsValueWithNonExisting();
    }

    private static void testContainsValueWithNull() {
        Properties props = new Properties();
        boolean result = false;
        try {
            result = props.containsValue(null);
        } catch (NullPointerException e) {
            System.out.println("containsValue(null) threw NullPointerException as expected.");
            return;
        }
        System.out.println("containsValue(null) returned: " + result);
    }

    private static void testContainsValueWithExisting() {
        Properties props = new Properties();
        String value = "testValue";
        props.put("key1", value);
        boolean result = props.containsValue(value);
        System.out.println("containsValue(existing value) returned: " + result);
    }

    private static void testContainsValueWithNonExisting() {
        Properties props = new Properties();
        props.put("key1", "value1");
        boolean result = props.containsValue("nonExistingValue");
        System.out.println("containsValue(non-existing value) returned: " + result);
    }
}
