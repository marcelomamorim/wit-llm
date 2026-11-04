/*
 * @test
 * @summary Verifies the observable checks performed by sun.net.www.URLConnection.addRequestProperty before and after connection
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

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/test");

        TestConnection c1 = new TestConnection(url);
        c1.addRequestProperty("X-Test", "value");
        c1.addRequestProperty("X-Null-Value", null);

        TestConnection c2 = new TestConnection(url);
        boolean npe = false;
        try {
            c2.addRequestProperty(null, "value");
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "expected NullPointerException for null key before connect");

        TestConnection c3 = new TestConnection(url);
        c3.connect();
        boolean ise = false;
        try {
            c3.addRequestProperty("X-Test", "value");
        } catch (IllegalStateException e) {
            ise = true;
        }
        check(ise, "expected IllegalStateException after connect");

        TestConnection c4 = new TestConnection(url);
        c4.connect();
        boolean iseNullKey = false;
        try {
            c4.addRequestProperty(null, "value");
        } catch (IllegalStateException e) {
            iseNullKey = true;
        } catch (NullPointerException e) {
            iseNullKey = false;
        }
        check(iseNullKey, "expected IllegalStateException (checked before key null check) after connect");
    }
}
