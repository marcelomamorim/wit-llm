/*
 * @test
 * @summary Test java.net.URLConnection.setDoOutput(boolean) behavior
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDoOutputBeforeConnect();
        testSetDoOutputAfterConnect();
    }

    static void testSetDoOutputBeforeConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Initially doOutput is false
        if (conn.getDoOutput()) {
            throw new RuntimeException("Expected doOutput to be false initially");
        }
        // Setting doOutput before connect should succeed
        conn.setDoOutput(true);
        if (!conn.getDoOutput()) {
            throw new RuntimeException("Expected doOutput to be true after setDoOutput(true)");
        }
    }

    static void testSetDoOutputAfterConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        try {
            conn.setDoOutput(true);
            throw new RuntimeException("Expected IllegalStateException when calling setDoOutput after connect");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
