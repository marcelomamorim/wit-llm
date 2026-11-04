/*
 * @test
 * @summary URLConnection.setDoOutput(boolean) stores the flag observable through getDoOutput()
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class TestConnection extends URLConnection {
        TestConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            connected = true;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection c = new TestConnection(url);

        c.setDoOutput(true);
        if (!c.getDoOutput()) {
            throw new AssertionError("getDoOutput() should be true after setDoOutput(true)");
        }

        c.setDoOutput(false);
        if (c.getDoOutput()) {
            throw new AssertionError("getDoOutput() should be false after setDoOutput(false)");
        }

        c.setDoOutput(true);
        if (!c.getDoOutput()) {
            throw new AssertionError("getDoOutput() should be true after second setDoOutput(true)");
        }
    }
}
