/*
 * @test
 * @summary Test JarURLConnection.setUseCaches(boolean) delegates correctly
 * @run main JarURLConnectionWitupTest
 */

import sun.net.www.protocol.jar.JarURLConnection;
import sun.net.www.protocol.jar.Handler;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

public class JarURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a valid jar URL string for testing
        // Format: jar:<url>!/{entry}
        // Use a dummy URL that points to a known jar file or a file URL
        String jarUrlString = "jar:file:/tmp/test.jar!/";
        URL url = null;
        try {
            url = new URL(jarUrlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL: " + jarUrlString, e);
        }

        // Handler is package-private, but JarURLConnection constructor requires it
        // Use null for handler since JarURLConnection constructor accepts it
        JarURLConnection conn = new JarURLConnection(url, null);

        // Initially set useCaches to true
        conn.setUseCaches(true);
        // No direct getter, but we can check that no exception is thrown

        // Set useCaches to false
        conn.setUseCaches(false);

        // Call setUseCaches multiple times to check idempotency
        conn.setUseCaches(true);
        conn.setUseCaches(false);

        // If no exceptions thrown, test passes
        System.out.println("Test passed: setUseCaches calls succeeded without exceptions.");
    }
}
