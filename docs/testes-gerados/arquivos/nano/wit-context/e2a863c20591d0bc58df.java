/*
 * @test
 * @summary Testa o método setProperty de java.util.Properties com entradas válidas e nulas.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testSetPropertyNormal();
        testSetPropertyNullValue();
    }

    static void testSetPropertyNormal() {
        Properties props = new Properties();
        String key = "testKey";
        String value = "testValue";
        Object result = props.setProperty(key, value);
        if (result != null) {
            System.out.println("PASS: setProperty with normal values");
        } else {
            System.out.println("FAIL: setProperty with normal values");
        }
        // Verifica se a propriedade foi definida corretamente
        if (props.getProperty(key).equals(value)) {
            System.out.println("PASS: property stored correctly");
        } else {
            System.out.println("FAIL: property not stored correctly");
        }
    }

    static void testSetPropertyNullValue() {
        Properties props = new Properties();
        String key = "nullValueKey";
        String value = null;
        Object result = null;
        try {
            result = props.setProperty(key, value);
            if (result != null) {
                System.out.println("PASS: setProperty with null value returns previous value (null)");
            } else {
                System.out.println("PASS: setProperty with null value returns null");
            }
            // Verifica se a propriedade foi definida como null
            if (props.getProperty(key) == null) {
                System.out.println("PASS: property with null value stored as null");
            } else {
                System.out.println("FAIL: property with null value not stored as null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown when setting null value: " + e);
        }
    }
}
