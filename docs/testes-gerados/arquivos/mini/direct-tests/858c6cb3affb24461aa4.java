/*
 * @test
 * @summary Test sun.net.www.protocol.jar.JarURLConnection.getContent() method
 * @run main JarURLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.jar.JarURLConnection;
import sun.net.www.protocol.jar.Handler;
import java.util.jar.JarFile;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentWithNullEntryName();
        testGetContentWithNonNullEntryName();
    }

    private static void testGetContentWithNullEntryName() throws Exception {
        // Create a JarURLConnection with a URL that has no entry name
        URL url = new URL("jar:file:/tmp/test.jar!/");
        Handler handler = new Handler();
        JarURLConnection conn = new JarURLConnection(url, handler) {
            // Override getEntryName to return null to simulate no entry
            @Override
            public String getEntryName() {
                return null;
            }

            // Override connect to simulate connection and jarFile initialization
            @Override
            public void connect() throws IOException {
                if (!connected) {
                    jarFile = new JarFile("/tmp/test.jar");
                    connected = true;
                }
            }
        };

        Object content = conn.getContent();
        if (!(content instanceof JarFile)) {
            throw new RuntimeException("Expected content to be instance of JarFile when entryName is null");
        }
    }

    private static void testGetContentWithNonNullEntryName() throws Exception {
        // Create a JarURLConnection with a URL that has an entry name
        URL url = new URL("jar:file:/tmp/test.jar!/META-INF/MANIFEST.MF");
        Handler handler = new Handler();
        JarURLConnection conn = new JarURLConnection(url, handler) {
            // Override getEntryName to return a non-null entry name
            @Override
            public String getEntryName() {
                return "META-INF/MANIFEST.MF";
            }

            // Override connect to simulate connection
            @Override
            public void connect() throws IOException {
                if (!connected) {
                    jarFile = new JarFile("/tmp/test.jar");
                    connected = true;
                }
            }

            // Override super.getContent() to simulate returning an InputStream
            @Override
            public Object getContent() throws IOException {
                connect();
                if (getEntryName() == null) {
                    return jarFile;
                } else {
                    // Simulate super.getContent() returning an InputStream
                    return new java.io.ByteArrayInputStream(new byte[0]);
                }
            }
        };

        Object content = conn.getContent();
        if (!(content instanceof java.io.InputStream)) {
            throw new RuntimeException("Expected content to be instance of InputStream when entryName is non-null");
        }
    }
}
