/*
 * @test
 * @summary Verify that JarURLConnection.setUseCaches(boolean) can be called repeatedly with both values without failing and leaves the connection usable
 * @modules java.base/sun.net.www.protocol.jar
 * @run main JarURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.jar.JarURLConnection;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("jar:file:/nonexistent/witup.jar!/entry.txt");
        JarURLConnection conn = new JarURLConnection(url, null);

        conn.setUseCaches(false);
        conn.setUseCaches(true);
        conn.setUseCaches(false);

        if (conn.getURL() == null || !url.equals(conn.getURL())) {
            throw new RuntimeException("URL changed after setUseCaches");
        }
    }
}
