/*
 * @test
 * @summary Test setReadTimeout with valid and invalid values
 * @run main MailToURLConnectionWitupTest
 */

import java.io.IOException;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws IOException {
        testSetReadTimeoutValid();
        testSetReadTimeoutNegative();
    }

    static void testSetReadTimeoutValid() {
        MailToURLConnection conn = new MailToURLConnection();
        try {
            conn.setReadTimeout(1000);
            System.out.println("setReadTimeout with 1000 succeeded");
        } catch (Exception e) {
            System.err.println("Unexpected exception for valid timeout: " + e);
        }
    }

    static void testSetReadTimeoutNegative() {
        MailToURLConnection conn = new MailToURLConnection();
        try {
            conn.setReadTimeout(-1);
            System.err.println("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly threw IllegalArgumentException for negative timeout");
        } catch (Exception e) {
            System.err.println("Unexpected exception type: " + e);
        }
    }
}
