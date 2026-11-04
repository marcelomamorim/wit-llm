/*
 * @test
 * @summary Test JarURLConnection.setUseCaches(boolean) behavior including IllegalStateException when connected
 * @run main JarURLConnectionWitupTest
 */

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.jar.JarURLConnection;
import sun.net.www.protocol.jar.Handler;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUseCachesBeforeConnect();
        testSetUseCachesAfterConnectThrows();
    }

    private static void testSetUseCachesBeforeConnect() throws Exception {
        URL url = new URL("jar:file:/tmp/dummy.jar!/entry");
        Handler handler = new Handler();
        JarURLConnection conn = new JarURLConnection(url, handler);

        // Initially not connected, should allow setUseCaches without exception
        conn.setUseCaches(true);
        conn.setUseCaches(false);
    }

    private static void testSetUseCachesAfterConnectThrows() throws Exception {
        URL url = new URL("jar:file:/tmp/dummy.jar!/entry");
        Handler handler = new Handler();
        JarURLConnection conn = new JarURLConnection(url, handler);

        // Force connect
        conn.connect();

        boolean threw = false;
        try {
            conn.setUseCaches(true);
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling setUseCaches after connect");
        }
    }
}
