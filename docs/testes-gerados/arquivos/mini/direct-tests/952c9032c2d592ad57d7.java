/*
 * @test
 * @summary Test getHeaderValue method throws RuntimeException as expected
 * @run main NegotiateAuthenticationWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.NegotiateAuthentication;

public class NegotiateAuthenticationWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy HttpCallerInfo to construct NegotiateAuthentication
        // Since HttpCallerInfo is not provided, we create a minimal stub class here
        HttpCallerInfoStub hci = new HttpCallerInfoStub();
        NegotiateAuthentication na = new NegotiateAuthentication(hci);

        URL url = new URL("http://example.com");
        String method = "GET";

        try {
            na.getHeaderValue(url, method);
            throw new RuntimeException("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (!"getHeaderValue not supported".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    // Minimal stub for HttpCallerInfo to allow construction
    static class HttpCallerInfoStub extends sun.net.www.protocol.http.HttpCallerInfo {
        public HttpCallerInfoStub() {
            super(null, null, null, 0, null, null, null, null, null);
        }
    }
}