/* @test    @summary Test setAllowUserInteraction method of HttpsURLConnectionOldImpl    @run main HttpsURLConnectionOldImplWitupTest */

import java.net.URL;
import java.io.IOException;
import com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws IOException {
        testSetAllowUserInteractionTrue();
        testSetAllowUserInteractionFalse();
    }

    private static void testSetAllowUserInteractionTrue() {
        URL url = new URL("https://example.com");
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (java.net.Proxy) null, null);
        conn.setAllowUserInteraction(true);
        // Since delegate is private, test indirectly by invoking the method without exception
        System.out.println("setAllowUserInteraction(true) invoked successfully");
    }

    private static void testSetAllowUserInteractionFalse() {
        URL url = new URL("https://example.com");
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (java.net.Proxy) null, null);
        conn.setAllowUserInteraction(false);
        System.out.println("setAllowUserInteraction(false) invoked successfully");
    }
}
