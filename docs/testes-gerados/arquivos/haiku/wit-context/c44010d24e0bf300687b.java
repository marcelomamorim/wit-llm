/*
 * @test
 * @summary Verifies that URLConnection.getInputStream() throws UnknownServiceException with the documented message when the protocol does not support input
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() throws IOException {
            }
        };
        for (int i = 0; i < 2; i++) {
            try {
                InputStream in = conn.getInputStream();
                throw new AssertionError("expected UnknownServiceException, got " + in);
            } catch (UnknownServiceException e) {
                if (!"protocol doesn't support input".equals(e.getMessage())) {
                    throw new AssertionError("unexpected message: " + e.getMessage());
                }
            }
        }
    }
}
