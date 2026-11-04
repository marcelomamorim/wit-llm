/*
 * @test
 * @summary Verify that URLConnection.getOutputStream() throws UnknownServiceException with the documented message when the protocol does not support output
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

public class URLConnectionWitupTest {

    static final class TestConnection extends URLConnection {
        TestConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection connection = new TestConnection(url);

        boolean thrown = false;
        try {
            OutputStream os = connection.getOutputStream();
            throw new RuntimeException("getOutputStream() returned " + os + " instead of throwing");
        } catch (UnknownServiceException e) {
            thrown = true;
            if (!"protocol doesn't support output".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
        if (!thrown) {
            throw new RuntimeException("UnknownServiceException was not thrown");
        }

        thrown = false;
        try {
            connection.getOutputStream();
        } catch (UnknownServiceException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("UnknownServiceException was not thrown on second call");
        }
    }
}
