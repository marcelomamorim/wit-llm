/*
 * @test
 * @summary URLConnection.getInputStream throws UnknownServiceException by default
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
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

    static void checkThrows(URLConnection c) throws IOException {
        try {
            InputStream in = c.getInputStream();
            throw new RuntimeException("expected UnknownServiceException, got stream: " + in);
        } catch (UnknownServiceException e) {
            if (!"protocol doesn't support input".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        URLConnection c = new StubConnection(url);
        checkThrows(c);
        checkThrows(c);
    }
}
