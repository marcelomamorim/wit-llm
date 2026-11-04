/*
 * @test
 * @summary Test getInputStream() method of JavaRuntimeURLConnection for expected behavior and exception
 * @run main JavaRuntimeURLConnectionWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import sun.net.www.protocol.jrt.JavaRuntimeURLConnection;

public class JavaRuntimeURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetInputStreamThrowsOnClosed();
    }

    private static void testGetInputStreamThrowsOnClosed() throws Exception {
        // Create a URL with a valid path to avoid MalformedURLException
        URL url = new URL("jrt:/java.base/java/lang/String.class");
        JavaRuntimeURLConnection conn = new JavaRuntimeURLConnection(url);

        // Close the connection to simulate the closed state
        conn.disconnect();

        try {
            conn.getInputStream();
            throw new AssertionError("Expected IllegalStateException when calling getInputStream() on closed connection");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
