/* @test    @summary Test setRequestProperty method of FtpURLConnection    @run main FtpURLConnectionWitupTest */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetRequestPropertyValidType();
        testSetRequestPropertyInvalidType();
    }

    static void testSetRequestPropertyValidType() throws Exception {
        URL url = new URL("ftp://example.com/file.txt");
        FtpURLConnection conn = (FtpURLConnection) url.openConnection();
        conn.setRequestProperty("type", "i");
        // Expect type to be BIN (2)
        if (conn.type != 2) {
            throw new AssertionError("Expected type to be BIN (2), but was " + conn.type);
        }
        conn.setRequestProperty("type", "a");
        if (conn.type != 1) {
            throw new AssertionError("Expected type to be ASCII (1), but was " + conn.type);
        }
        conn.setRequestProperty("type", "d");
        if (conn.type != 3) {
            throw new AssertionError("Expected type to be DIR (3), but was " + conn.type);
        }
    }

    static void testSetRequestPropertyInvalidType() throws Exception {
        URL url = new URL("ftp://example.com/file.txt");
        FtpURLConnection conn = (FtpURLConnection) url.openConnection();
        boolean exceptionThrown = false;
        try {
            conn.setRequestProperty("type", "x");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid type value");
        }
    }
}
