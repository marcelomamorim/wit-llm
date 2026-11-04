/*
 * @test
 * @summary Verifies addRequestProperty guard behavior for not-connected and connected states and for null keys
 * @modules java.base/sun.net.www
 * @run main URLConnectionWitupTest
 */

import java.net.URL;

public class URLConnectionWitupTest {

    static class StubConnection extends sun.net.www.URLConnection {
        StubConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() {
            connected = true;
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        URL u = new URL("http://localhost/");

        StubConnection valid = new StubConnection(u);
        valid.addRequestProperty("key", "value");

        StubConnection nullKey = new StubConnection(u);
        try {
            nullKey.addRequestProperty(null, "value");
            fail("NullPointerException expected for null key when not connected");
        } catch (NullPointerException e) {
        }

        StubConnection connectedValid = new StubConnection(u);
        connectedValid.connect();
        try {
            connectedValid.addRequestProperty("key", "value");
            fail("IllegalStateException expected when connected");
        } catch (IllegalStateException e) {
        }

        StubConnection connectedNullKey = new StubConnection(u);
        connectedNullKey.connect();
        try {
            connectedNullKey.addRequestProperty(null, "value");
            fail("IllegalStateException expected when connected even with null key");
        } catch (IllegalStateException e) {
        }
    }
}
