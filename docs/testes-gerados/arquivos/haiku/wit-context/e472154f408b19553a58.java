/*
 * @test
 * @summary Verifies that JarURLConnection.setUseCaches delegates without throwing on an unconnected jar connection
 * @modules java.base/sun.net.www.protocol.jar
 * @run main JarURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.jar.JarURLConnection;

public class JarURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("jar:file:/nonexistent/witup.jar!/entry.txt");
        JarURLConnection c = new JarURLConnection(url, null);
        c.setUseCaches(false);
        c.setUseCaches(true);
        c.setUseCaches(false);
    }
}
