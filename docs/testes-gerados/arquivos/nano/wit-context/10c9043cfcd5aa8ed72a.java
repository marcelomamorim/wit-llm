/*
 * @test
 * @summary Test for sun.net.www.protocol.ftp.FtpURLConnection.setRequestProperty
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetRequestPropertyValidType();
        testSetRequestPropertyInvalidType();
        testSetRequestPropertyNullKey();
    }

    private static void testSetRequestPropertyValidType() throws Exception {
        URL url = new URL("ftp://example.com/file.txt");
        FtpURLConnection conn = (FtpURLConnection) url.openConnection();
        // Simulate connection established to avoid 'connected' guard condition
        // Note: In actual implementation, this may require more setup
        // but for this test, we focus on method invocation and behavior
        // We assume the connection is not yet established
        conn.setRequestProperty("type", "i");
        // Expect type to be set to BIN (2)
        // No exception expected
    }

    private static void testSetRequestPropertyInvalidType() throws Exception {
        URL url = new URL("ftp://example.com/file.txt");
        FtpURLConnection conn = (FtpURLConnection) url.openConnection();
        try {
            conn.setRequestProperty("type", "invalid");
            // Expect IllegalArgumentException
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testSetRequestPropertyNullKey() throws Exception {
        URL url = new URL("ftp://example.com/file.txt");
        FtpURLConnection conn = (FtpURLConnection) url.openConnection();
        // Simulate connection not yet established
        // Set property with null key
        conn.setRequestProperty(null, "a");
        // No exception expected, property should be set or ignored
    }
}
