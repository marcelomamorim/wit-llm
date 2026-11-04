/*
 * @test
 * @summary URLConnection.getOutputStream must throw UnknownServiceException with the protocol message when the protocol does not support output
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

public class URLConnectionWitupTest {

    static class StubConnection extends URLConnection {
        StubConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/resource");
        StubConnection conn = new StubConnection(url);

        boolean thrown = false;
        try {
            OutputStream os = conn.getOutputStream();
            throw new RuntimeException("expected UnknownServiceException but got stream: " + os);
        } catch (UnknownServiceException e) {
            thrown = true;
            if (!"protocol doesn't support output".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("UnknownServiceException was not thrown");
        }

        // Repeated calls must behave the same way (no state change observable).
        try {
            conn.getOutputStream();
            throw new RuntimeException("second call did not throw");
        } catch (UnknownServiceException e) {
            if (!"protocol doesn't support output".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message on second call: " + e.getMessage());
            }
        }

        if (!url.equals(conn.getURL())) {
            throw new RuntimeException("getURL() does not match constructor URL");
        }
    }
}
