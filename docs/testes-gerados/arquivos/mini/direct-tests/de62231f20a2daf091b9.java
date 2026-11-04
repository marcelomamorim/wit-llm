/*
 * @test
 * @summary Test getInputStream method of JavaRuntimeURLConnection
 * @run main JavaRuntimeURLConnectionWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import sun.net.www.protocol.jrt.JavaRuntimeURLConnection;

public class JavaRuntimeURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testMalformedURL();
        testGetInputStreamReturnsStream();
    }

    private static void testMalformedURL() {
        try {
            new JavaRuntimeURLConnection(new URL("jrt:/"));
            throw new RuntimeException("Expected MalformedURLException for missing path or /");
        } catch (MalformedURLException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testGetInputStreamReturnsStream() throws Exception {
        // Use a URL with a path that has module and resource name
        // We cannot guarantee the resource exists, so test only that getInputStream returns InputStream or throws IOException
        URL url = new URL("jrt:/java.base/java/lang/String.class");
        JavaRuntimeURLConnection conn = new JavaRuntimeURLConnection(url);
        conn.connect();
        InputStream is = conn.getInputStream();
        if (is == null) {
            throw new RuntimeException("getInputStream returned null");
        }
        // read at least one byte or check available
        int available = is.available();
        if (available < 0) {
            throw new RuntimeException("InputStream available() returned negative");
        }
        is.close();
    }
}
