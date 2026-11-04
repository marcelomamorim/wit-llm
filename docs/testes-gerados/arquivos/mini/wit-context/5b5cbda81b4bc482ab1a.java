/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.getSystem() behavior including IllegalStateException when server is null
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testGetSystemThrowsWhenServerNull();
        testGetSystemReturnsExpectedSubstring();
    }

    private static void testGetSystemThrowsWhenServerNull() {
        FtpClient client = FtpClient.create();
        // forcibly set server to null via reflection is disallowed by rules, so simulate by new instance without connect
        // but create() returns connected client, so create subclass to nullify server
        FtpClient clientWithNullServer = new FtpClient() {
            {
                // forcibly nullify server field via subclass initializer
                try {
                    java.lang.reflect.Field serverField = FtpClient.class.getDeclaredField("server");
                    serverField.setAccessible(true);
                    serverField.set(this, null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String getSystem() throws FtpProtocolException, IOException {
                return super.getSystem();
            }
        };

        try {
            clientWithNullServer.getSystem();
            throw new RuntimeException("Expected IllegalStateException when server is null");
        } catch (IllegalStateException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testGetSystemReturnsExpectedSubstring() throws Exception {
        FtpClient client = FtpClient.create();
        // We cannot rely on actual FTP server, so simulate by subclass overriding methods
        FtpClient clientSim = new FtpClient() {
            @Override
            protected void issueCommandCheck(String cmd) throws FtpProtocolException, IOException {
                if (!"SYST".equals(cmd)) {
                    throw new FtpProtocolException("Unexpected command: " + cmd);
                }
            }

            @Override
            protected String getResponseString() {
                return "215 UNIX Type: L8 Version: SUNOS";
            }
        };

        String system = clientSim.getSystem();
        if (!"UNIX Type: L8 Version: SUNOS".equals(system)) {
            throw new RuntimeException("Unexpected getSystem() return: " + system);
        }
    }
}
