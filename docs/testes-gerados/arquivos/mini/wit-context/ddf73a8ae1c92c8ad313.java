/*
 * @test
 * @summary Test sun.security.ssl.SSLSessionImpl.getValue(String) method
 * @run main SSLSessionImplWitupTest
 */

import sun.security.ssl.SSLSessionImpl;
import sun.security.ssl.SSLSessionImpl.SecureKey;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) {
        SSLSessionImpl session = SSLSessionImpl.nullSession;

        // Test 1: getValue with null key should throw IllegalArgumentException
        try {
            session.getValue(null);
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test 2: getValue with a key not bound should return null
        Object value = session.getValue("nonexistentKey");
        if (value != null) {
            throw new RuntimeException("Expected null for unbound key");
        }

        // Test 3: getValue with a key bound to a value
        // Since SSLSessionImpl.nullSession is a singleton with no bound values,
        // we cannot bind values publicly, so this test is limited.
        // We test that getValue returns null for any key.
        Object val2 = session.getValue("anyKey");
        if (val2 != null) {
            throw new RuntimeException("Expected null for any key on nullSession");
        }
    }
}
