/*
 * @test
 * @summary Test java.net.URLConnection.setUseCaches(boolean) behavior
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetUseCachesBeforeConnect();
        testSetUseCachesAfterConnect();
    }

    static void testSetUseCachesBeforeConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Should allow setUseCaches before connect
        conn.setUseCaches(true);
        if (!conn.getUseCaches()) {
            throw new RuntimeException("setUseCaches(true) did not set the flag correctly before connect");
        }
        conn.setUseCaches(false);
        if (conn.getUseCaches()) {
            throw new RuntimeException("setUseCaches(false) did not set the flag correctly before connect");
        }
    }

    static void testSetUseCachesAfterConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        try {
            conn.setUseCaches(true);
            throw new RuntimeException("Expected IllegalStateException not thrown after connect");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}
