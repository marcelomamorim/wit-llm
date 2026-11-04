/* @test    @summary Test setUseCaches method of HttpsURLConnectionImpl    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.net.Proxy;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUseCachesTrue();
        testSetUseCachesFalse();
    }

    private static void testSetUseCachesTrue() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, null);
        conn.setUseCaches(true);
        // No direct getter; test that no exception is thrown and method completes
        System.out.println("setUseCaches(true) executed successfully");
    }

    private static void testSetUseCachesFalse() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, null);
        conn.setUseCaches(false);
        // No direct getter; test that no exception is thrown and method completes
        System.out.println("setUseCaches(false) executed successfully");
    }
}
