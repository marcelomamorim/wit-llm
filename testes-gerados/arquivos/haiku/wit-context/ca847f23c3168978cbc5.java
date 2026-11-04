/*
 * @test
 * @summary Verifies setRequestProperty for valid keys, null keys and the already connected state
 * @modules java.base/sun.net.www
 * @run main URLConnectionWitupTest
 */
import java.net.URL;
import sun.net.www.URLConnection;

public class URLConnectionWitupTest {

    static class TestConnection extends URLConnection {
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

        TestConnection valid = new TestConnection(url);
        valid.setRequestProperty("X-Test", "value");
        String stored = valid.getProperties().findValue("X-Test");
        if (!"value".equals(stored)) {
            throw new RuntimeException("Expected stored value 'value' but got: " + stored);
        }

        TestConnection nullKey = new TestConnection(url);
        try {
            nullKey.setRequestProperty(null, "v");
            throw new RuntimeException("NullPointerException expected for null key");
        } catch (NullPointerException expected) {
        }

        TestConnection connected = new TestConnection(url);
        connected.connect();
        try {
            connected.setRequestProperty("X-Test", "v");
            throw new RuntimeException("IllegalAccessError expected when already connected");
        } catch (IllegalAccessError expected) {
        }

        TestConnection connectedNullKey = new TestConnection(url);
        connectedNullKey.connect();
        try {
            connectedNullKey.setRequestProperty(null, "v");
            throw new RuntimeException("IllegalAccessError expected when connected even with null key");
        } catch (IllegalAccessError expected) {
        }
    }
}
