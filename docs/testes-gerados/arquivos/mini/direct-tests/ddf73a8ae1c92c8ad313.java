/*
 * @test
 * @summary Test sun.security.ssl.SSLSessionImpl.getValue(String) method
 * @run main SSLSessionImplWitupTest
 */

import sun.security.ssl.SSLSessionImpl;
import sun.security.ssl.SSLSessionImpl.SecureKey;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetValueWithNullKey();
        testGetValueWithAbsentKey();
        testGetValueWithPresentKey();
    }

    private static void testGetValueWithNullKey() {
        SSLSessionImpl session = SSLSessionImpl.nullSession;
        try {
            session.getValue(null);
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testGetValueWithAbsentKey() {
        SSLSessionImpl session = SSLSessionImpl.nullSession;
        Object value = session.getValue("nonexistentKey");
        if (value != null) {
            throw new RuntimeException("Expected null for absent key");
        }
    }

    private static void testGetValueWithPresentKey() throws Exception {
        // We need to add a value to boundValues map to test retrieval
        // boundValues is private, so we use the public putValue method
        SSLSessionImpl session = SSLSessionImpl.nullSession;
        String key = "testKey";
        String val = "testValue";
        session.putValue(key, val);
        Object retrieved = session.getValue(key);
        if (!val.equals(retrieved)) {
            throw new RuntimeException("Expected to retrieve the value just put");
        }
    }
}
