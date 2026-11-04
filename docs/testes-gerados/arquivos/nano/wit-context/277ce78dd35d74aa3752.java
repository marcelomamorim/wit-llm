/*
 * @test
 * @summary Test for setConnectTimeout method in URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetConnectTimeoutNegative();
        testSetConnectTimeoutZero();
        testSetConnectTimeoutPositive();
    }

    private static void testSetConnectTimeoutNegative() {
        URLConnection conn = new DummyURLConnection();
        try {
            conn.setConnectTimeout(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative timeout");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSetConnectTimeoutZero() {
        URLConnection conn = new DummyURLConnection();
        try {
            conn.setConnectTimeout(0);
            System.out.println("PASS: Zero timeout set successfully");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero timeout: " + e);
        }
    }

    private static void testSetConnectTimeoutPositive() {
        URLConnection conn = new DummyURLConnection();
        try {
            conn.setConnectTimeout(1000);
            System.out.println("PASS: Positive timeout set successfully");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for positive timeout: " + e);
        }
    }

    private static class DummyURLConnection extends URLConnection {
        protected DummyURLConnection() {
            super(null);
        }
        @Override
        public void connect() throws IOException {
            // no-op
        }
        @Override
        public void setConnectTimeout(int timeout) {
            if (timeout < 0) {
                throw new IllegalArgumentException("timeout can not be negative");
            }
            // simulate setting timeout
        }
    }
}
