/*
 * @test
 * @summary Test java.net.URLConnection.getContent(URLConnection) method
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Test 1: getContent returns InputStream from a valid URLConnection
        URL url = new URL("http://www.example.com/");
        URLConnection uc = url.openConnection();
        uc.connect();
        Object content = URLConnection.getContent(uc);
        if (!(content instanceof InputStream)) {
            throw new AssertionError("Expected InputStream from getContent, got: " + content);
        }
        ((InputStream) content).close();

        // Test 2: getContent throws IOException if getInputStream throws IOException
        URLConnection failingUC = new URLConnection(url) {
            @Override
            public void connect() throws IOException {
                // no-op
            }

            @Override
            public InputStream getInputStream() throws IOException {
                throw new IOException("Simulated IOException");
            }
        };
        failingUC.connect();
        boolean threw = false;
        try {
            URLConnection.getContent(failingUC);
        } catch (IOException e) {
            threw = true;
            if (!"Simulated IOException".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IOException was not thrown");
        }
    }
}
