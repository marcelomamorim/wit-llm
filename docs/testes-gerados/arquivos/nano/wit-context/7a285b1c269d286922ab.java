/*
 * @test
 * @summary Test for URLConnection.getOutputStream() method.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.io.OutputStream;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testGetOutputStreamThrowsException();
    }

    public static void testGetOutputStreamThrowsException() {
        URLConnection conn = new URLConnection(null) {
            @Override
            public void connect() throws IOException {
                // no-op
            }
        };
        try {
            OutputStream os = conn.getOutputStream();
            // Since the method always throws, reaching this line indicates failure
            System.err.println("Expected UnknownServiceException was not thrown.");
            System.exit(1);
        } catch (IOException e) {
            if (e instanceof java.net.UnknownServiceException) {
                // Test passes
                System.out.println("Caught expected UnknownServiceException.");
            } else {
                System.err.println("Caught unexpected exception: " + e);
                System.exit(1);
            }
        }
    }
}
