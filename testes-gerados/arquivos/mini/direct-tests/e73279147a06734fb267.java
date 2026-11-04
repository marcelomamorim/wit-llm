/*
 * @test
 * @summary Test java.net.URLConnection.setAllowUserInteraction(boolean) behavior
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetAllowUserInteractionBeforeConnect();
        testSetAllowUserInteractionAfterConnect();
    }

    static void testSetAllowUserInteractionBeforeConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Should allow setting allowUserInteraction before connect
        conn.setAllowUserInteraction(true);
        if (!conn.getAllowUserInteraction()) {
            throw new RuntimeException("Expected allowUserInteraction to be true after setAllowUserInteraction(true)");
        }
        conn.setAllowUserInteraction(false);
        if (conn.getAllowUserInteraction()) {
            throw new RuntimeException("Expected allowUserInteraction to be false after setAllowUserInteraction(false)");
        }
    }

    static void testSetAllowUserInteractionAfterConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        try {
            conn.setAllowUserInteraction(true);
            throw new RuntimeException("Expected IllegalStateException when calling setAllowUserInteraction after connect");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}