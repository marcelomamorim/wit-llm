/*
 * @test
 * @summary Verifies getRequestProperty returns null before connection and throws IllegalStateException once connected
 * @modules java.base/sun.net.www
 * @run main URLConnectionWitupTest
 */

import java.net.URL;

public class URLConnectionWitupTest {

    static class TestConnection extends sun.net.www.URLConnection {
        TestConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() {
            connected = true;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/test");
        TestConnection c = new TestConnection(url);

        if (c.getRequestProperty("Accept") != null) {
            throw new AssertionError("Expected null before connect");
        }
        if (c.getRequestProperty(null) != null) {
            throw new AssertionError("Expected null for null key before connect");
        }

        c.connect();

        try {
            c.getRequestProperty("Accept");
            throw new AssertionError("Expected IllegalStateException after connect");
        } catch (IllegalStateException expected) {
            // expected
        }

        try {
            c.getRequestProperty(null);
            throw new AssertionError("Expected IllegalStateException for null key after connect");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
