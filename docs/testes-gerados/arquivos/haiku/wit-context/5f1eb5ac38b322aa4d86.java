/*
 * @test
 * @summary Verify HttpURLConnection.setRequestMethod accepts valid methods and rejects invalid methods and changes after connect
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
            connected = true;
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");

        TestConnection defaults = new TestConnection(url);
        if (!"GET".equals(defaults.getRequestMethod())) {
            throw new RuntimeException("Default method should be GET");
        }

        String[] valid = {"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"};
        for (String m : valid) {
            TestConnection c = new TestConnection(url);
            c.setRequestMethod(m);
            if (!m.equals(c.getRequestMethod())) {
                throw new RuntimeException("Method not set: " + m);
            }
        }

        String[] invalid = {"FOO", "get", "Post", ""};
        for (String m : invalid) {
            TestConnection c = new TestConnection(url);
            try {
                c.setRequestMethod(m);
                throw new RuntimeException("ProtocolException expected for: '" + m + "'");
            } catch (ProtocolException expected) {
                // expected
            }
            if (!"GET".equals(c.getRequestMethod())) {
                throw new RuntimeException("Method changed after invalid input: '" + m + "'");
            }
        }

        TestConnection nullCase = new TestConnection(url);
        try {
            nullCase.setRequestMethod(null);
            throw new RuntimeException("ProtocolException expected for null");
        } catch (ProtocolException expected) {
            // expected
        }
        if (!"GET".equals(nullCase.getRequestMethod())) {
            throw new RuntimeException("Method changed after null input");
        }

        TestConnection connected = new TestConnection(url);
        connected.connect();
        try {
            connected.setRequestMethod("POST");
            throw new RuntimeException("ProtocolException expected when already connected");
        } catch (ProtocolException expected) {
            // expected
        }
        if (!"GET".equals(connected.getRequestMethod())) {
            throw new RuntimeException("Method changed after connect");
        }
    }
}
