/* @test    @summary Test setReadTimeout method of MailToURLConnection for valid and invalid inputs    @run main MailToURLConnectionWitupTest */

import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetReadTimeoutValid();
        testSetReadTimeoutNegative();
    }

    static void testSetReadTimeoutValid() {
        MailToURLConnection conn = new MailToURLConnection(null);
        try {
            conn.setReadTimeout(1000);
            assert conn != null : "Connection should not be null";
            System.out.println("testSetReadTimeoutValid passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid timeout", e);
        }
    }

    static void testSetReadTimeoutNegative() {
        MailToURLConnection conn = new MailToURLConnection(null);
        boolean thrown = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } else {
            System.out.println("testSetReadTimeoutNegative passed.");
        }
    }
}
