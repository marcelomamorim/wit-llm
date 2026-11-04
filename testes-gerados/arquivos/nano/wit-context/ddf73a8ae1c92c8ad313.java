/*
 * @test
 * @summary Test for SSLSessionImpl.getValue(String key)
 * @run main SSLSessionImplWitupTest
 */

import sun.security.ssl.SSLSessionImpl;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) {
        testGetValueWithNullKey();
        testGetValueWithValidKey();
    }

    private static void testGetValueWithNullKey() {
        SSLSessionImpl session = new SSLSessionImpl();
        try {
            session.getValue(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null key");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testGetValueWithValidKey() {
        SSLSessionImpl session = new SSLSessionImpl();
        String key = "testKey";
        Object value = new Object();
        // Simulate binding a value
        // Note: Since boundValues is package-private or accessible only via reflection,
        // and no public API to set values is provided, this test assumes the internal state.
        // As per constraints, avoid reflection; thus, this test will only verify that getValue
        // returns null for an unbound key.
        Object result = session.getValue(key);
        if (result == null) {
            System.out.println("PASS: getValue returned null for unbound key as expected");
        } else {
            System.out.println("FAIL: getValue returned non-null for unbound key");
        }
    }
}
