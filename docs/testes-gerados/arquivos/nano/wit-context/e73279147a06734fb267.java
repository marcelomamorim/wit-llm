/*
 * @test
 * @summary Test for setAllowUserInteraction method in URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetAllowUserInteractionConnected();
        testSetAllowUserInteractionNotConnected();
    }

    private static void testSetAllowUserInteractionConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Simulate connected state by invoking connect()
        conn.connect();
        try {
            conn.setAllowUserInteraction(true);
            System.out.println("Connected state: setAllowUserInteraction executed without exception.");
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException when connected", e);
        }
    }

    private static void testSetAllowUserInteractionNotConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Not calling connect(), so should not be connected
        try {
            conn.setAllowUserInteraction(true);
            System.out.println("Not connected state: setAllowUserInteraction executed without exception.");
        } catch (IllegalStateException e) {
            // Expected if the method checks connection state
            System.out.println("Not connected state: Caught expected IllegalStateException.");
        }
    }
}
