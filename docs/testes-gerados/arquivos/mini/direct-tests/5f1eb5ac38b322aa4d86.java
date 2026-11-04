/*
 * @test
 * @summary Test HttpURLConnection.setRequestMethod behavior
 * @run main HttpURLConnectionWitupTest
 */

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.SecurityManager;

public class HttpURLConnectionWitupTest {

    static class DummyHttpURLConnection extends HttpURLConnection {
        protected DummyHttpURLConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() {
            connected = true;
        }

        @Override
        public void disconnect() {}

        @Override
        public boolean usingProxy() { return false; }
    }

    public static void main(String[] args) throws Exception {
        testSetRequestMethodValid();
        testSetRequestMethodInvalid();
        testSetRequestMethodAfterConnected();
        testSetRequestMethodTraceWithSecurityManager();
        testSetRequestMethodTraceWithoutSecurityManager();
    }

    private static void testSetRequestMethodValid() throws Exception {
        DummyHttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        String[] validMethods = {"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"};
        for (String m : validMethods) {
            conn.connected = false;
            conn.setRequestMethod(m);
            if (!conn.method.equals(m)) {
                throw new RuntimeException("Failed to set valid method: " + m);
            }
        }
    }

    private static void testSetRequestMethodInvalid() throws Exception {
        DummyHttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        conn.connected = false;
        try {
            conn.setRequestMethod("INVALIDMETHOD");
            throw new RuntimeException("Expected ProtocolException for invalid method");
        } catch (ProtocolException e) {
            // expected
        }
    }

    private static void testSetRequestMethodAfterConnected() throws Exception {
        DummyHttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        conn.connected = true;
        try {
            conn.setRequestMethod("GET");
            throw new RuntimeException("Expected ProtocolException when setting method after connected");
        } catch (ProtocolException e) {
            // expected
        }
    }

    private static void testSetRequestMethodTraceWithSecurityManager() throws Exception {
        DummyHttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        conn.connected = false;

        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof java.net.NetPermission && "allowHttpTrace".equals(perm.getName())) {
                    throw new SecurityException("No allowHttpTrace permission");
                }
            }
        });

        try {
            try {
                conn.setRequestMethod("TRACE");
                throw new RuntimeException("Expected SecurityException for TRACE without permission");
            } catch (SecurityException se) {
                // expected
            }
        } finally {
            System.setSecurityManager(original);
        }
    }

    private static void testSetRequestMethodTraceWithoutSecurityManager() throws Exception {
        DummyHttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        conn.connected = false;

        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(null);

        try {
            conn.setRequestMethod("TRACE");
            if (!"TRACE".equals(conn.method)) {
                throw new RuntimeException("Failed to set TRACE method without SecurityManager");
            }
        } finally {
            System.setSecurityManager(original);
        }
    }
}
