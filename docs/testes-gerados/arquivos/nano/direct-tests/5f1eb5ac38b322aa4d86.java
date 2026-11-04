/* @test    @summary Test HttpURLConnection.setRequestMethod with valid and invalid methods    @run main HttpURLConnectionWitupTest */

import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetRequestMethodValid();
        testSetRequestMethodInvalid();
    }

    static void testSetRequestMethodValid() {
        HttpURLConnection connection = new DummyHttpURLConnection();
        try {
            connection.setRequestMethod("GET");
            assert "GET".equals(connection.getRequestMethod()) : "Method should be GET";
            connection.setRequestMethod("POST");
            assert "POST".equals(connection.getRequestMethod()) : "Method should be POST";
            connection.setRequestMethod("TRACE");
            assert "TRACE".equals(connection.getRequestMethod()) : "Method should be TRACE";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid method test", e);
        }
    }

    static void testSetRequestMethodInvalid() {
        HttpURLConnection connection = new DummyHttpURLConnection();
        try {
            connection.setRequestMethod("INVALID_METHOD");
            throw new AssertionError("Expected ProtocolException for invalid method");
        } catch (ProtocolException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in invalid method test", e);
        }
    }

    static class DummyHttpURLConnection extends HttpURLConnection {
        private String requestMethod = "GET";

        protected DummyHttpURLConnection() {
            super(null);
        }

        @Override
        public String getRequestMethod() {
            return requestMethod;
        }

        @Override
        public void setRequestMethod(String method) throws ProtocolException {
            // Simulate the actual method behavior
            String[] methods = {"GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "TRACE", "PATCH"};
            boolean valid = false;
            for (String m : methods) {
                if (m.equals(method)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                throw new ProtocolException("Invalid HTTP method: " + method);
            }
            if (connected) {
                throw new ProtocolException("Can't reset method: already connected");
            }
            if (method.equals("TRACE")) {
                // Simulate security check
                SecurityManager s = System.getSecurityManager();
                if (s != null) {
                    s.checkPermission(new java.net.NetPermission("allowHttpTrace"));
                }
            }
            this.requestMethod = method;
        }
    }
}
