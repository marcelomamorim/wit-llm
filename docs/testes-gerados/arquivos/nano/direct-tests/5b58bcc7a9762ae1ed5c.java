/*
 * @test    @summary Test getInputStream() method of URLClassPath for valid URL
 * @run main URLClassPathWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import jdk.internal.loader.URLClassPath;

public class URLClassPathWitupTest {
    public static void main(String[] args) throws Exception {
        testGetInputStreamWithValidURL();
        testGetInputStreamWithInvalidURL();
    }

    private static void testGetInputStreamWithValidURL() throws Exception {
        URL url = new URL("file:///tmp/");
        URLConnection uc = url.openConnection();
        URLClassPath ucp = new URLClassPath(new URL[] { url }, null);
        InputStream is = ucp.getInputStream();
        if (is == null) {
            throw new AssertionError("Expected non-null InputStream for valid URL");
        }
        is.close();
    }

    private static void testGetInputStreamWithInvalidURL() {
        try {
            URL url = new URL("invalid:///");
            URLClassPath ucp = new URLClassPath(new URL[] { url }, null);
            ucp.getInputStream();
            throw new AssertionError("Expected IOException for invalid URL");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
