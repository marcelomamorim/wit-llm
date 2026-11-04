/* @test    @summary Test removeValue method of SSLSessionImpl for null argument and normal operation    @run main SSLSessionImplWitupTest */

import sun.security.ssl.SSLSessionImpl;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionBindingEvent;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) {
        testRemoveValueNull();
        testRemoveValueNormal();
    }

    static void testRemoveValueNull() {
        SSLSessionImpl session = new SSLSessionImpl();
        boolean exceptionThrown = false;
        try {
            session.removeValue(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null key");
        }
    }

    static void testRemoveValueNormal() {
        SSLSessionImpl session = new SSLSessionImpl();
        String key = "testKey";
        Object value = new Object();
        // Simulate adding a value with SSLSessionBindingListener
        session.boundValues.put(new SecureKey(key), value);
        // Add listener to value
        if (value instanceof SSLSessionBindingListener) {
            // Not necessary here, but for completeness
        }
        // Remove the value
        session.removeValue(key);
        // Verify removal
        if (session.boundValues.containsKey(new SecureKey(key))) {
            throw new AssertionError("Value was not removed");
        }
    }

    // Dummy SecureKey class to simulate key wrapping
    static class SecureKey {
        private final String key;
        SecureKey(String key) {
            this.key = key;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            SecureKey other = (SecureKey) obj;
            return key.equals(other.key);
        }
        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }
}
