/*
 * @test
 * @summary Test sun.net.www.protocol.ftp.FtpURLConnection.setRequestProperty behavior
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("ftp://localhost/");
        FtpURLConnection con = new FtpURLConnection(url);

        // Test setting type to 'i' (binary)
        con.setRequestProperty("type", "i");
        if (con.type != 2) {
            throw new RuntimeException("Expected type BIN (2) for 'i', got " + con.type);
        }

        // Test setting type to 'I' (binary, case insensitive)
        con.setRequestProperty("type", "I");
        if (con.type != 2) {
            throw new RuntimeException("Expected type BIN (2) for 'I', got " + con.type);
        }

        // Test setting type to 'a' (ASCII)
        con.setRequestProperty("type", "a");
        if (con.type != 1) {
            throw new RuntimeException("Expected type ASCII (1) for 'a', got " + con.type);
        }

        // Test setting type to 'A' (ASCII, case insensitive)
        con.setRequestProperty("type", "A");
        if (con.type != 1) {
            throw new RuntimeException("Expected type ASCII (1) for 'A', got " + con.type);
        }

        // Test setting type to 'd' (DIR)
        con.setRequestProperty("type", "d");
        if (con.type != 3) {
            throw new RuntimeException("Expected type DIR (3) for 'd', got " + con.type);
        }

        // Test setting type to 'D' (DIR, case insensitive)
        con.setRequestProperty("type", "D");
        if (con.type != 3) {
            throw new RuntimeException("Expected type DIR (3) for 'D', got " + con.type);
        }

        // Test setting unrelated property does not change type
        int oldType = con.type;
        con.setRequestProperty("other", "value");
        if (con.type != oldType) {
            throw new RuntimeException("Type changed unexpectedly when setting unrelated property");
        }

        // Test setting invalid type value throws IllegalArgumentException
        boolean threw = false;
        try {
            con.setRequestProperty("type", "x");
        } catch (IllegalArgumentException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("type") || !msg.contains("x")) {
                throw new RuntimeException("Exception message does not contain expected text: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for invalid type value");
        }

        System.out.println("All tests passed.");
    }
}
