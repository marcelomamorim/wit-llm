/*
 * @test
 * @summary Verifies URLConnection.setDoInput guard: allowed before connect, IllegalStateException after connected
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class TestConnection extends URLConnection {
        TestConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() {
            connected = true;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");

        TestConnection notConnected = new TestConnection(url);
        if (!notConnected.getDoInput()) {
            throw new RuntimeException("Default doInput expected true");
        }
        notConnected.setDoInput(false);
        if (notConnected.getDoInput()) {
            throw new RuntimeException("setDoInput(false) before connect not applied");
        }
        notConnected.setDoInput(true);
        if (!notConnected.getDoInput()) {
            throw new RuntimeException("setDoInput(true) before connect not applied");
        }

        TestConnection connected = new TestConnection(url);
        connected.connect();
        boolean thrown = false;
        try {
            connected.setDoInput(false);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from setDoInput after connect");
        }
        if (!connected.getDoInput()) {
            throw new RuntimeException("doInput changed despite IllegalStateException");
        }
    }
}
