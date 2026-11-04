/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.getContent()
 * @run main HttpsURLConnectionImplWitupTest
 */

import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetContentReturnsDelegateContent();
        testCheckURLRejectsNewlineInURL();
        testConstructorRejectsMalformedURL();
    }

    private static void testGetContentReturnsDelegateContent() throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com");
        // Create instance with null proxy and null handler (handler is package private, so use null)
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, null);

        // The delegate is internal, but getContent() should not throw and return an Object
        Object content = conn.getContent();

        if (content == null) {
            throw new RuntimeException("getContent() returned null, expected non-null or valid behavior");
        }
    }

    private static void testCheckURLRejectsNewlineInURL() {
        try {
            URL badUrl = new URL("https://example.com/path\nwithnewline");
            // checkURL is package private static, so we cannot call it directly here
            // Instead, try to create HttpsURLConnectionImpl with bad URL to trigger checkURL
            new HttpsURLConnectionImpl(badUrl, (Proxy) null, null);
            throw new RuntimeException("Expected MalformedURLException for URL with newline");
        } catch (MalformedURLException e) {
            // Expected
        } catch (IOException e) {
            // IOException is superclass, but MalformedURLException expected
            if (!(e instanceof MalformedURLException)) {
                throw new RuntimeException("Expected MalformedURLException but got " + e);
            }
        }
    }

    private static void testConstructorRejectsMalformedURL() {
        try {
            // URL with newline in string to cause MalformedURLException
            URL badUrl = new URL("http://bad\nurl");
            new HttpsURLConnectionImpl(badUrl, (Proxy) null, null);
            throw new RuntimeException("Expected MalformedURLException for malformed URL");
        } catch (MalformedURLException e) {
            // Expected
        } catch (IOException e) {
            if (!(e instanceof MalformedURLException)) {
                throw new RuntimeException("Expected MalformedURLException but got " + e);
            }
        }
    }
}
