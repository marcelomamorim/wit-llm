/*
 * @test
 * @summary Test getOutputStream() method of URLConnection for exception throwing
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.io.IOException;
import java.io.UnknownServiceException;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testGetOutputStreamThrowsException();
    }

    public static void testGetOutputStreamThrowsException() {
        URLConnection connection = new URLConnection(null) {
            @Override
            public void connect() throws IOException {
                // no-op
            }
        };
        try {
            connection.getOutputStream();
        } catch (IOException e) {
            if (e instanceof UnknownServiceException) {
                System.out.println("Test passed: getOutputStream() throws UnknownServiceException as expected.");
            } else {
                throw new AssertionError("Unexpected exception type: " + e.getClass().getName(), e);
            }
        }
    }
}
