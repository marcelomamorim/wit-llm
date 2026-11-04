/*
 * @test
 * @summary Test sun.net.www.protocol.mailto.MailToURLConnection.getOutputStream()
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import java.io.OutputStream;
import java.io.IOException;
import sun.net.www.protocol.mailto.MailToURLConnection;
import sun.net.www.ParseUtil;

public class MailToURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetOutputStreamReturnsSameInstance();
        testGetOutputStreamThrowsAfterInputStreamSet();
    }

    private static void testGetOutputStreamReturnsSameInstance() throws Exception {
        URL url = new URL("mailto:test@example.com");
        MailToURLConnection conn = new MailToURLConnection(url);

        OutputStream os1 = conn.getOutputStream();
        if (os1 == null) {
            throw new RuntimeException("getOutputStream() returned null");
        }

        OutputStream os2 = conn.getOutputStream();
        if (os1 != os2) {
            throw new RuntimeException("getOutputStream() did not return the same OutputStream instance on second call");
        }
    }

    private static void testGetOutputStreamThrowsAfterInputStreamSet() throws Exception {
        URL url = new URL("mailto:test@example.com");
        MailToURLConnection conn = new MailToURLConnection(url);

        // Use reflection to set the private field 'is' to non-null to simulate input stream already read
        java.lang.reflect.Field isField = MailToURLConnection.class.getDeclaredField("is");
        isField.setAccessible(true);
        isField.set(conn, new java.io.ByteArrayInputStream(new byte[0]));

        try {
            conn.getOutputStream();
            throw new RuntimeException("Expected IOException not thrown when input stream is already set");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Cannot write output after reading input.")) {
                throw new RuntimeException("IOException thrown with unexpected message: " + msg);
            }
        }
    }
}
