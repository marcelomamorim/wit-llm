/*
 * @test
 * @summary Verifies that URLConnection.setUseCaches stores the value returned by getUseCaches before connecting
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
        }
    }

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection c = new TestConnection(url);

        c.setUseCaches(false);
        check(!c.getUseCaches(), "useCaches should be false after setUseCaches(false)");

        c.setUseCaches(true);
        check(c.getUseCaches(), "useCaches should be true after setUseCaches(true)");

        c.setUseCaches(false);
        check(!c.getUseCaches(), "useCaches should be false after toggling back to false");

        c.setUseCaches(true);
        check(c.getUseCaches(), "useCaches should be true after toggling back to true");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
