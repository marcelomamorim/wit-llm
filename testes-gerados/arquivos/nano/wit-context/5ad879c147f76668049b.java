/*
 * @test
 * @summary Testa o método put de java.util.Properties para entradas nulas.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testPutWithNullKey();
        testPutWithNullValue();
        testPutWithNonNulls();
    }

    static void testPutWithNullKey() {
        Properties props = new Properties();
        Object result = null;
        try {
            result = props.put(null, "value");
            // Espera-se que não lance exceção, pois put aceita null como key
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException for null key", e);
        }
        // Pode verificar se o valor foi inserido
        Object stored = props.get(null);
        if (stored == null || !stored.equals("value")) {
            throw new AssertionError("Value not stored correctly for null key");
        }
    }

    static void testPutWithNullValue() {
        Properties props = new Properties();
        Object result = null;
        try {
            result = props.put("key", null);
            // Espera-se que não lance exceção, pois put aceita null como value
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException for null value", e);
        }
        Object stored = props.get("key");
        if (stored != null) {
            throw new AssertionError("Value not stored correctly for null value");
        }
    }

    static void testPutWithNonNulls() {
        Properties props = new Properties();
        Object result = props.put("k", "v");
        if (result != null) {
            throw new AssertionError("Expected null as previous value for new key");
        }
        Object stored = props.get("k");
        if (!"v".equals(stored)) {
            throw new AssertionError("Stored value mismatch");
        }
    }
}
