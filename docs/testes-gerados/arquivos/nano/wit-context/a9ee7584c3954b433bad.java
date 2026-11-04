/*
 * @test
 * @summary Test setConnectTimeout with valid and invalid values
 * @run main MailToURLConnectionWitupTest
 */

import java.io.IOException;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetConnectTimeoutValid();
        testSetConnectTimeoutNegative();
    }

    static void testSetConnectTimeoutValid() {
        MailToURLConnection conn = new MailToURLConnection();
        try {
            conn.setConnectTimeout(1000);
            assert conn != null : "Connection object should not be null";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid timeout", e);
        }
    }

    static void testSetConnectTimeoutNegative() {
        MailToURLConnection conn = new MailToURLConnection();
        try {
            conn.setConnectTimeout(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("timeouts can't be negative") : "Exception message mismatch";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
