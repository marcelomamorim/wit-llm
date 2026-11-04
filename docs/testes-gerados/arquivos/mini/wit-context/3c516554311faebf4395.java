/*
 * @test
 * @summary Test java.net.URLConnection.setUseCaches(boolean) behavior and exception
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetUseCachesBeforeConnect();
        testSetUseCachesAfterConnectThrows();
    }

    private static void testSetUseCachesBeforeConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Initially, setUseCaches should succeed before connect
        conn.setUseCaches(true);
        if (!conn.getUseCaches()) {
            throw new AssertionError("Expected useCaches to be true after setUseCaches(true)");
        }
        conn.setUseCaches(false);
        if (conn.getUseCaches()) {
            throw new AssertionError("Expected useCaches to be false after setUseCaches(false)");
        }
    }

    private static void testSetUseCachesAfterConnectThrows() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        try {
            conn.setUseCaches(true);
            throw new AssertionError("Expected IllegalStateException when calling setUseCaches after connect");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
        try {
            conn.setUseCaches(false);
            throw new AssertionError("Expected IllegalStateException when calling setUseCaches after connect");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
