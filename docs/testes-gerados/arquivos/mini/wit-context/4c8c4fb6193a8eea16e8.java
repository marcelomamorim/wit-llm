/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setDoOutput(boolean) behavior
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import sun.net.www.protocol.https.Handler;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetDoOutputBeforeConnect();
    }

    private static void testSetDoOutputBeforeConnect() throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com/");
        // Use null proxy and null handler as Handler is package-private and no public factory
        Handler handler = new Handler();
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, handler);

        // Initially, setDoOutput should succeed without exception
        conn.setDoOutput(true);
        conn.setDoOutput(false);

        // The delegate is expected to throw IllegalStateException if connected
        // But we cannot force connected state easily without internal access
        // So we test only the observable behavior before connection
    }
}
