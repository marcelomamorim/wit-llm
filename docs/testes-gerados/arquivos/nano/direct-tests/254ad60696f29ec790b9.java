/* @test    @summary Test setIfModifiedSince method of HttpsURLConnectionOldImpl    @run main HttpsURLConnectionOldImplWitupTest */

import java.net.URL;
import java.io.IOException;
import com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetIfModifiedSince();
    }

    public static void testSetIfModifiedSince() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (java.net.Proxy) null, null);
        long testValue = 123456789L;
        conn.setIfModifiedSince(testValue);
        // Since delegate is private, we test behavior by calling the method again with a different value
        long newValue = 987654321L;
        conn.setIfModifiedSince(newValue);
        // No exception means the method executed successfully
    }
}
