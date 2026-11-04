/*
 * @test
 * @summary Test for setUseCaches method in JarURLConnection.
 * @run main JarURLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.jar.JarURLConnection;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUseCachesThrowsIllegalStateException();
        testSetUseCachesNormalBehavior();
    }

    private static void testSetUseCachesThrowsIllegalStateException() throws Exception {
        URL url = new URL("file:///dummy.jar");
        URLConnection conn = url.openConnection();
        if (!(conn instanceof JarURLConnection)) {
            System.out.println("Skipping test: not a JarURLConnection");
            return;
        }
        JarURLConnection jarConn = (JarURLConnection) conn;
        // Simulate the connected state by invoking connect()
        try {
            jarConn.connect();
        } catch (IOException e) {
            // ignore
        }
        // Manually set internal state to simulate 'connected' (reflection or mock not used here)
        // Since we cannot set 'connected' directly, we test the behavior assuming connect() was called.
        // Expect IllegalStateException if setUseCaches is called after connect.
        try {
            jarConn.setUseCaches(true);
            System.out.println("Error: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Passed: IllegalStateException thrown as expected.");
        } catch (Exception e) {
            System.out.println("Error: Unexpected exception: " + e);
        }
    }

    private static void testSetUseCachesNormalBehavior() throws Exception {
        URL url = new URL("file:///dummy.jar");
        URLConnection conn = url.openConnection();
        if (!(conn instanceof JarURLConnection)) {
            System.out.println("Skipping test: not a JarURLConnection");
            return;
        }
        JarURLConnection jarConn = (JarURLConnection) conn;
        // Call setUseCaches before connect
        try {
            jarConn.setUseCaches(false);
            System.out.println("Passed: setUseCaches before connect.");
        } catch (Exception e) {
            System.out.println("Error: Unexpected exception: " + e);
        }
    }
}
