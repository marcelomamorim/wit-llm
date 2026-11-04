/*
 * @test
 * @summary Test HttpURLConnection.setRequestMethod behavior including exceptions
 * @run main HttpURLConnectionWitupTest
 */

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.SecurityManager;

public class HttpURLConnectionWitupTest {

    // Minimal subclass to instantiate abstract HttpURLConnection
    static class MyHttpURLConnection extends HttpURLConnection {
        protected MyHttpURLConnection(URL u) {
            super(u);
        }

        @Override
        public void disconnect() {}

        @Override
        public boolean usingProxy() { return false; }

        @Override
        public void connect() {}
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com");
        MyHttpURLConnection conn = new MyHttpURLConnection(url);

        // Test valid methods from the allowed methods array
        String[] validMethods = {"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"};
        for (String m : validMethods) {
            // Reset connected to false to allow method change
            conn.connected = false;
            // Clear method to default
            conn.method = "GET";

            if ("TRACE".equals(m)) {
                // Test security manager check for TRACE
                SecurityManager original = System.getSecurityManager();
                try {
                    System.setSecurityManager(new SecurityManager() {
                        @Override
                        public void checkPermission(Permission perm) {
                            if ("allowHttpTrace".equals(perm.getName())) {
                                throw new SecurityException("TRACE not allowed");
                            }
                        }
                    });
                    try {
                        conn.setRequestMethod(m);
                        throw new RuntimeException("Expected SecurityException for TRACE method");
                    } catch (SecurityException se) {
                        // Expected
                    }
                } finally {
                    System.setSecurityManager(original);
                }
            } else {
                conn.setRequestMethod(m);
                if (!conn.method.equals(m)) {
                    throw new RuntimeException("Method not set correctly: expected " + m + " but was " + conn.method);
                }
            }
        }

        // Test invalid method throws ProtocolException
        conn.connected = false;
        try {
            conn.setRequestMethod("INVALID_METHOD");
            throw new RuntimeException("Expected ProtocolException for invalid method");
        } catch (ProtocolException pe) {
            // Expected
        }

        // Test that setRequestMethod throws ProtocolException if connected is true
        conn.connected = true;
        try {
            conn.setRequestMethod("GET");
            throw new RuntimeException("Expected ProtocolException when connected is true");
        } catch (ProtocolException pe) {
            // Expected
        }

        System.out.println("HttpURLConnectionWitupTest passed");
    }
}
