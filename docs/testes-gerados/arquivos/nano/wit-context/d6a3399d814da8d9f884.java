/*
 * @test
 * @summary Tests for java.net.URL.openConnection()
 * @run main URLWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy;
import java.io.IOException;

public class URLWitupTest {

    public static void main(String[] args) throws Exception {
        testOpenConnectionWithNullProxy();
        testOpenConnectionWithNoProxy();
        testOpenConnectionWithDirectProxy();
    }

    private static void testOpenConnectionWithNullProxy() {
        try {
            URL url = new URL("http://example.com");
            URLConnection conn = url.openConnection();
            if (conn == null) {
                throw new AssertionError("Expected non-null URLConnection");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testOpenConnectionWithNoProxy() {
        try {
            URL url = new URL("http://example.com");
            URLConnection conn = url.openConnection();
            if (conn == null) {
                throw new AssertionError("Expected non-null URLConnection");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testOpenConnectionWithDirectProxy() {
        try {
            URL url = new URL("http://example.com");
            Proxy proxy = Proxy.NO_PROXY;
            URLConnection conn = url.openConnection(proxy);
            if (conn == null) {
                throw new AssertionError("Expected non-null URLConnection");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
