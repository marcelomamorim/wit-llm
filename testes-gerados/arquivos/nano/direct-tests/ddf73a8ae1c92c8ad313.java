/*
 * @test
 * @summary Test getValue method of SSLSessionImpl for various keys.
 * @run main SSLSessionImplWitupTest
 */

import sun.security.ssl.SSLSessionImpl;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) {
        testGetValueWithNullKey();
        testGetValueWithValidKey();
        testGetValueWithInvalidKey();
    }

    private static void testGetValueWithNullKey() {
        SSLSessionImpl session = new SSLSessionImpl();
        boolean exceptionThrown = false;
        try {
            session.getValue(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null key");
        }
    }

    private static void testGetValueWithValidKey() {
        SSLSessionImpl session = new SSLSessionImpl();
        String key = "testKey";
        Object value = new Object();
        // Using reflection to set the boundValues map for testing purposes
        try {
            java.lang.reflect.Field boundValuesField = SSLSessionImpl.class.getDeclaredField("boundValues");
            boundValuesField.setAccessible(true);
            java.util.Map<Object, Object> boundValues = new java.util.HashMap<>();
            boundValues.put(new SecureKey(key), value);
            boundValuesField.set(session, boundValues);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection setup failed", e);
        }
        Object retrieved = session.getValue(key);
        if (retrieved != value) {
            throw new AssertionError("getValue did not return the expected value");
        }
    }

    private static void testGetValueWithInvalidKey() {
        SSLSessionImpl session = new SSLSessionImpl();
        String key = "nonexistentKey";
        // Using reflection to set the boundValues map for testing purposes
        try {
            java.lang.reflect.Field boundValuesField = SSLSessionImpl.class.getDeclaredField("boundValues");
            boundValuesField.setAccessible(true);
            java.util.Map<Object, Object> boundValues = new java.util.HashMap<>();
            // No entries added
            boundValuesField.set(session, boundValues);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection setup failed", e);
        }
        Object result = session.getValue(key);
        if (result != null) {
            throw new AssertionError("Expected null for nonexistent key");
        }
    }
}
