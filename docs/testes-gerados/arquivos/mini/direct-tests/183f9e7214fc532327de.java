/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setIfModifiedSince(long)
 * @run main HttpsURLConnectionImplWitupTest
 */

import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com");
        // Use null proxy and null handler (handler is package private, so use null)
        // Use the protected constructor via subclass to test setIfModifiedSince
        TestHttpsURLConnectionImpl conn = new TestHttpsURLConnectionImpl(url);

        // Test setting a normal positive timestamp
        conn.setIfModifiedSince(123456789L);

        // Test setting zero
        conn.setIfModifiedSince(0L);

        // Test setting negative value (should be accepted as no exception in code)
        conn.setIfModifiedSince(-1L);

        // If no exceptions thrown, test passes
        System.out.println("All setIfModifiedSince tests passed.");
    }

    // Subclass to expose protected constructor
    static class TestHttpsURLConnectionImpl extends HttpsURLConnectionImpl {
        TestHttpsURLConnectionImpl(URL u) throws IOException {
            super(u);
        }

        // Expose setIfModifiedSince publicly
        @Override
        public void setIfModifiedSince(long ifmodifiedsince) {
            super.setIfModifiedSince(ifmodifiedsince);
        }
    }
}
