/*
 * @test
 * @summary Test sun.security.ssl.SSLSessionImpl.removeValue method behavior
 * @run main SSLSessionImplWitupTest
 */

import sun.security.ssl.SSLSessionImpl;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionBindingEvent;

public class SSLSessionImplWitupTest {

    static class TestBindingListener implements SSLSessionBindingListener {
        boolean unboundCalled = false;
        String unboundName = null;

        @Override
        public void valueBound(SSLSessionBindingEvent event) {
            // Not used in this test
        }

        @Override
        public void valueUnbound(SSLSessionBindingEvent event) {
            unboundCalled = true;
            unboundName = event.getName();
        }
    }

    public static void main(String[] args) throws Exception {
        SSLSessionImpl session = new SSLSessionImpl();

        // Test 1: removeValue with null key throws IllegalArgumentException
        try {
            session.removeValue(null);
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test 2: removeValue with key not bound does nothing (no exception)
        session.removeValue("nonexistentKey");

        // Test 3: removeValue with bound value that is not SSLSessionBindingListener
        Object dummyValue = new Object();
        session.putValue("dummyKey", dummyValue);
        session.removeValue("dummyKey");

        // Test 4: removeValue with bound value that is SSLSessionBindingListener triggers valueUnbound
        TestBindingListener listener = new TestBindingListener();
        session.putValue("listenerKey", listener);
        session.removeValue("listenerKey");

        if (!listener.unboundCalled) {
            throw new RuntimeException("valueUnbound was not called on SSLSessionBindingListener");
        }
        if (!"listenerKey".equals(listener.unboundName)) {
            throw new RuntimeException("valueUnbound event name mismatch");
        }
    }
}
