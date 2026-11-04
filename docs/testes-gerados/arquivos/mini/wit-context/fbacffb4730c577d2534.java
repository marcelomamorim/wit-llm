/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setDoInput(boolean) behavior
 * @run main HttpsURLConnectionImplWitupTest
 */

import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDoInputBeforeConnect();
        testSetDoInputAfterConnect();
    }

    private static void testSetDoInputBeforeConnect() throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com");
        // Use null proxy and null handler as Handler is not public, so use null
        // Use the protected constructor via subclass to test
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url) {
            // Expose connect method to simulate connection
            public void connect() throws IOException {
                super.connect();
            }
        };

        // Initially, setDoInput should not throw
        conn.setDoInput(true);
        conn.setDoInput(false);
    }

    private static void testSetDoInputAfterConnect() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url) {
            private boolean connected = false;
            @Override
            public void connect() throws IOException {
                connected = true;
            }
            @Override
            public boolean getDoInput() {
                return true;
            }
            @Override
            public void setDoInput(boolean doinput) {
                if (connected) {
                    throw new IllegalStateException("Already connected");
                }
                super.setDoInput(doinput);
            }
        };

        conn.connect();

        boolean threw = false;
        try {
            conn.setDoInput(true);
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling setDoInput after connect");
        }
    }
}
