/*
 * @test
 * @summary Verify sun.net.www.URLConnection.getRequestProperty returns null when not connected and throws IllegalStateException when connected
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
        URL url = new URL("http://localhost/");

        TestConnection notConnected = new TestConnection(url);
        String value = notConnected.getRequestProperty("Accept");
        if (value != null) {
            throw new RuntimeException("Expected null when not connected, got: " + value);
        }

        TestConnection connected = new TestConnection(url);
        connected.connect();
        try {
            connected.getRequestProperty("Accept");
            throw new RuntimeException("Expected IllegalStateException when connected");
        } catch (IllegalStateException expected) {
        }
    }
}
