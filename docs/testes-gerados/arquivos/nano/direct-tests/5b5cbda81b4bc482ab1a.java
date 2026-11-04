/*
 * @test
 * @summary Test getSystem() method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientWitupTest
 */

import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testGetSystemReturnsExpectedSubstring();
    }

    private static void testGetSystemReturnsExpectedSubstring() throws Exception {
        FtpClient ftpClient = createMockFtpClient();
        String response = "215 UNIX Type: L8 Version: SUNOS";
        // Simulate the response string from getResponseString()
        setResponseString(ftpClient, response);
        String systemType = ftpClient.getSystem();
        if (!"UNIX Type: L8 Version: SUNOS".equals(systemType)) {
            throw new AssertionError("Expected 'UNIX Type: L8 Version: SUNOS', but got: " + systemType);
        }
        System.out.println("getSystem() test passed.");
    }

    private static FtpClient createMockFtpClient() throws Exception {
        // Instantiate FtpClient via protected constructor
        FtpClient client = new FtpClient() {
            @Override
            protected String getResponseString() {
                return responseString;
            }
        };
        return client;
    }

    private static String responseString;

    private static void setResponseString(FtpClient client, String response) throws Exception {
        // Use reflection to set the private responseString field if needed
        // But since getResponseString() is overridden, assign responseString
        responseString = response;
    }
}
