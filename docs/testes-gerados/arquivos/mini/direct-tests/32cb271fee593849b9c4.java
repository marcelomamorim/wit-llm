/*
 * @test
 * @summary Test java.net.URLConnection.setIfModifiedSince(long) behavior
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetIfModifiedSinceBeforeConnect();
        testSetIfModifiedSinceAfterConnect();
    }

    static void testSetIfModifiedSinceBeforeConnect() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // setIfModifiedSince before connect should succeed
        long testTime = 123456789L;
        conn.setIfModifiedSince(testTime);
        if (conn.getIfModifiedSince() != testTime) {
            throw new AssertionError("setIfModifiedSince did not set the value correctly before connect");
        }
    }

    static void testSetIfModifiedSinceAfterConnect() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        try {
            conn.setIfModifiedSince(987654321L);
            throw new AssertionError("Expected IllegalStateException after connect");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
