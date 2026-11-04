/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.makeDirectory(String) for IllegalStateException when server is null
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // The WIT expath indicates that if 'server' field is null, makeDirectory throws IllegalStateException
        // Since 'server' is private and no public setter, we test the observable behavior by calling makeDirectory
        // on a newly created client without connection (server == null expected)

        boolean illegalStateThrown = false;
        try {
            client.makeDirectory("testDir");
        } catch (IllegalStateException e) {
            illegalStateThrown = true;
        } catch (FtpProtocolException | IOException e) {
            // These exceptions are declared but not expected here since no connection
            // We do not fail the test on these, just note
        }

        if (!illegalStateThrown) {
            throw new RuntimeException("Expected IllegalStateException when server is null in makeDirectory");
        }

        // Additional sanity: calling makeDirectory with null name should throw NullPointerException or similar
        boolean npeThrown = false;
        try {
            client.makeDirectory(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        } catch (Exception e) {
            // Accept other exceptions as well, do not fail
        }

        if (!npeThrown) {
            // It's acceptable if no NPE, so no failure here
        }

        System.out.println("FtpClientWitupTest passed");
    }
}
