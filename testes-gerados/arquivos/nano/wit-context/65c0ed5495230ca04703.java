/*
 * @test
 * @summary Test for Properties.putIfAbsent handling null keys or values
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentWithNullKey();
        testPutIfAbsentWithNullValue();
        testPutIfAbsentWithValidEntries();
    }

    private static void testPutIfAbsentWithNullKey() {
        Properties props = new Properties();
        try {
            props.putIfAbsent(null, "value");
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null key as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null key: " + e);
        }
    }

    private static void testPutIfAbsentWithNullValue() {
        Properties props = new Properties();
        try {
            props.putIfAbsent("key", null);
            System.out.println("PASS: Null value accepted in putIfAbsent");
        } catch (NullPointerException e) {
            System.out.println("FAIL: NullPointerException thrown for null value");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null value: " + e);
        }
    }

    private static void testPutIfAbsentWithValidEntries() {
        Properties props = new Properties();
        Object result1 = props.putIfAbsent("key1", "value1");
        if (result1 == null && "value1".equals(props.get("key1"))) {
            System.out.println("PASS: putIfAbsent added new key-value pair");
        } else {
            System.out.println("FAIL: putIfAbsent did not add new key-value pair as expected");
        }
        Object result2 = props.putIfAbsent("key1", "value2");
        if (result2 != null && result2.equals("value1") && "value1".equals(props.get("key1"))) {
            System.out.println("PASS: putIfAbsent did not overwrite existing key");
        } else {
            System.out.println("FAIL: putIfAbsent overwrote existing key or returned wrong value");
        }
    }
}
