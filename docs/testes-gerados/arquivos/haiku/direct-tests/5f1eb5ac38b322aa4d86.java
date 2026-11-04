/*
 * @test
 * @summary Verifies HttpURLConnection.setRequestMethod accepts valid methods, rejects invalid ones and enforces the connected state
 * @run main HttpURLConnectionWitupTest
 */

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    static class TestConnection extends HttpURLConnection {
        TestConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() {
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        void markConnected() {
            connected = true;
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");

        String[] valid = {"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"};
        for (String m : valid) {
            TestConnection c = new TestConnection(url);
            c.setRequestMethod(m);
            if (!m.equals(c.method)) {
                fail("Method not set: " + m + " got " + c.method);
            }
        }

        String[] invalid = {"FOO", "get", "", "CONNECT"};
        for (String m : invalid) {
            TestConnection c = new TestConnection(url);
            try {
                c.setRequestMethod(m);
                fail("ProtocolException expected for: '" + m + "'");
            } catch (ProtocolException expected) {
                if (!"GET".equals(c.method)) {
                    fail("Method changed after invalid input: " + c.method);
                }
            }
        }

        TestConnection nullCase = new TestConnection(url);
        try {
            nullCase.setRequestMethod(null);
            fail("ProtocolException expected for null method");
        } catch (ProtocolException expected) {
            if (!"GET".equals(nullCase.method)) {
                fail("Method changed after null input");
            }
        }

        TestConnection connected = new TestConnection(url);
        connected.markConnected();
        try {
            connected.setRequestMethod("POST");
            fail("ProtocolException expected when already connected");
        } catch (ProtocolException expected) {
            if (!"GET".equals(connected.method)) {
                fail("Method changed while connected");
            }
        }
    }
}
