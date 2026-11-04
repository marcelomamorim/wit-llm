/*
 * @test
 * @summary Test setUseCaches method of JarURLConnection
 * @run main JarURLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.jar.JarURLConnection;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUseCachesTrue();
        testSetUseCachesFalse();
    }

    static void testSetUseCachesTrue() throws MalformedURLException, IOException {
        URL url = new URL("jar:file:/dummy/path!/entry");
        URLConnection conn = url.openConnection();
        if (!(conn instanceof JarURLConnection)) {
            throw new AssertionError("Connection is not a JarURLConnection");
        }
        JarURLConnection jarConn = (JarURLConnection) conn;
        jarConn.setUseCaches(true);
        // Verify that the underlying URLConnection has setUseCaches true
        if (!jarConn.jarFileURLConnection.getUseCaches()) {
            throw new AssertionError("Expected useCaches to be true");
        }
    }

    static void testSetUseCachesFalse() throws MalformedURLException, IOException {
        URL url = new URL("jar:file:/dummy/path!/entry");
        URLConnection conn = url.openConnection();
        if (!(conn instanceof JarURLConnection)) {
            throw new AssertionError("Connection is not a JarURLConnection");
        }
        JarURLConnection jarConn = (JarURLConnection) conn;
        jarConn.setUseCaches(false);
        // Verify that the underlying URLConnection has setUseCaches false
        if (jarConn.jarFileURLConnection.getUseCaches()) {
            throw new AssertionError("Expected useCaches to be false");
        }
    }
}
