/*
 * @test
 * @summary Test sun.net.www.protocol.http.NegotiateAuthentication.getHeaderValue throws RuntimeException
 * @run main NegotiateAuthenticationWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.NegotiateAuthentication;

public class NegotiateAuthenticationWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy HttpCallerInfo to construct NegotiateAuthentication
        // Since HttpCallerInfo is not public or constructor unknown, we skip it and test getHeaderValue directly
        // getHeaderValue is an instance method, so we create a minimal subclass to call it

        NegotiateAuthentication na = new NegotiateAuthenticationStub();

        boolean thrown = false;
        try {
            na.getHeaderValue(new URL("http://example.com"), "GET");
        } catch (RuntimeException e) {
            if ("getHeaderValue not supported".equals(e.getMessage())) {
                thrown = true;
            } else {
                throw e; // unexpected RuntimeException message
            }
        }

        if (!thrown) {
            throw new AssertionError("Expected RuntimeException was not thrown by getHeaderValue");
        }
    }

    // Minimal subclass to call getHeaderValue since constructor requires HttpCallerInfo
    static class NegotiateAuthenticationStub extends NegotiateAuthentication {
        NegotiateAuthenticationStub() {
            super(new HttpCallerInfoStub());
        }

        // Override getHeaderValue to call super method
        @Override
        public String getHeaderValue(URL url, String method) {
            return super.getHeaderValue(url, method);
        }
    }

    // Stub for HttpCallerInfo with minimal implementation
    static class HttpCallerInfoStub extends sun.net.www.protocol.http.HttpCallerInfo {
        HttpCallerInfoStub() {
            super(null, null, null, 0, null, null, null, null, null);
        }
    }
}
