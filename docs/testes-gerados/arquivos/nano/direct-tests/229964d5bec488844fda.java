/*
 * @test
 * @summary Test setReadTimeout method for URLConnection
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetReadTimeoutPositive();
        testSetReadTimeoutZero();
        testSetReadTimeoutNegative();
    }

    static void testSetReadTimeoutPositive() {
        try {
            URL url = new URL("http://example.com");
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(1000);
            assert conn != null : "Connection should not be null";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in positive timeout test", e);
        }
    }

    static void testSetReadTimeoutZero() {
        try {
            URL url = new URL("http://example.com");
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(0);
            assert conn != null : "Connection should not be null";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in zero timeout test", e);
        }
    }

    static void testSetReadTimeoutNegative() {
        try {
            URL url = new URL("http://example.com");
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in negative timeout test", e);
        }
    }
}
