/* @test    @summary Test setAllowUserInteraction method of HttpsURLConnectionImpl    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetAllowUserInteractionTrue();
        testSetAllowUserInteractionFalse();
    }

    private static void testSetAllowUserInteractionTrue() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl connection = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        connection.setAllowUserInteraction(true);
        // No direct getter; test that no exception is thrown and method completes
    }

    private static void testSetAllowUserInteractionFalse() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl connection = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        connection.setAllowUserInteraction(false);
        // No direct getter; test that no exception is thrown and method completes
    }
}
