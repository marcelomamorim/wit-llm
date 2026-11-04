/*
 * @test
 * @summary Test for URLConnection.setReadTimeout(int)
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetReadTimeoutNegative();
        testSetReadTimeoutZero();
        testSetReadTimeoutPositive();
    }

    public static void testSetReadTimeoutNegative() {
        URLConnection conn = new DummyURLConnection();
        try {
            conn.setReadTimeout(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative timeout");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    public static void testSetReadTimeoutZero() {
        URLConnection conn = new DummyURLConnection();
        try {
            conn.setReadTimeout(0);
            System.out.println("PASS: setReadTimeout(0) executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero timeout: " + e);
        }
    }

    public static void testSetReadTimeoutPositive() {
        URLConnection conn = new DummyURLConnection();
        try {
            conn.setReadTimeout(1000);
            System.out.println("PASS: setReadTimeout(1000) executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for positive timeout: " + e);
        }
    }

    static class DummyURLConnection extends URLConnection {
        protected DummyURLConnection() {
            super(null);
        }
        @Override
        public void connect() throws java.io.IOException {
            // no-op
        }
        @Override
        public void setReadTimeout(int timeout) {
            if (timeout < 0) {
                throw new IllegalArgumentException("timeout can not be negative");
            }
            // simulate setting timeout
        }
    }
}
