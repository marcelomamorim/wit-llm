/*
 * @test
 * @summary setRequestProperty stores request properties, rejects null keys and throws IllegalAccessError when already connected
 * @modules java.base/sun.net.www
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;

public class URLConnectionWitupTest {

    static class TestConnection extends sun.net.www.URLConnection {
        TestConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() throws IOException {
            connected = true;
        }

        void markConnected() {
            connected = true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/test");

        // 1. A property set before connecting is stored and readable.
        TestConnection c = new TestConnection(url);
        c.setRequestProperty("Accept", "text/html");
        check("text/html".equals(c.getProperties().findValue("Accept")),
              "Accept header not stored");

        // 2. Setting the same key again replaces the value.
        c.setRequestProperty("Accept", "text/plain");
        check("text/plain".equals(c.getProperties().findValue("Accept")),
              "Accept header not replaced");

        // 3. A null key must raise NullPointerException.
        TestConnection c2 = new TestConnection(url);
        boolean npe = false;
        try {
            c2.setRequestProperty(null, "value");
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null key did not throw NullPointerException");

        // 4. After connecting, setRequestProperty must throw IllegalAccessError
        //    and leave the existing properties unchanged.
        TestConnection c3 = new TestConnection(url);
        c3.setRequestProperty("X-Test", "before");
        c3.markConnected();
        boolean iae = false;
        try {
            c3.setRequestProperty("X-Test", "after");
        } catch (IllegalAccessError e) {
            iae = "Already connected".equals(e.getMessage());
        }
        check(iae, "connected connection did not throw IllegalAccessError");
        check("before".equals(c3.getProperties().findValue("X-Test")),
              "property modified after connection");
    }
}
