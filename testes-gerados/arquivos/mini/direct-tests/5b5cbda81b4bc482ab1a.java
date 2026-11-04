/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.getSystem() method behavior
 * @run main FtpClientWitupTest
 */

import java.io.IOException;
import sun.net.ftp.FtpProtocolException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testGetSystemReturnsCorrectSubstring();
        testGetSystemThrowsOnNullResponse();
    }

    private static void testGetSystemReturnsCorrectSubstring() throws Exception {
        // Create a subclass to override methods for controlled test
        FtpClient client = new FtpClient() {
            @Override
            protected void issueCommandCheck(String cmd) throws IOException, FtpProtocolException {
                if (!"SYST".equals(cmd)) {
                    throw new IOException("Unexpected command: " + cmd);
                }
                // no-op for test
            }

            @Override
            protected String getResponseString() {
                // Return a valid response string with 3-digit code + space + system info
                return "215 UNIX Type: L8 Version: SUNOS";
            }
        };

        String system = client.getSystem();
        if (!"UNIX Type: L8 Version: SUNOS".equals(system)) {
            throw new AssertionError("Expected 'UNIX Type: L8 Version: SUNOS' but got: " + system);
        }
    }

    private static void testGetSystemThrowsOnNullResponse() throws Exception {
        FtpClient client = new FtpClient() {
            @Override
            protected void issueCommandCheck(String cmd) throws IOException, FtpProtocolException {
                // no-op
            }

            @Override
            protected String getResponseString() {
                return null; // simulate null response
            }
        };

        try {
            client.getSystem();
            throw new AssertionError("Expected NullPointerException due to null response");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
