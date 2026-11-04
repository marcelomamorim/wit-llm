/*
 * @test
 * @summary Test the siteCmd method of sun.net.ftp.impl.FtpClient for normal command execution.
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import java.io.IOException;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testSiteCmdNormal();
        testSiteCmdNull();
        testSiteCmdEmpty();
    }

    private static void testSiteCmdNormal() {
        try {
            FtpClient client = new FtpClient();
            FtpClient result = client.siteCmd("CHMOD 755 file.txt");
            if (result != null) {
                System.out.println("testSiteCmdNormal passed");
            } else {
                System.out.println("testSiteCmdNormal failed: result is null");
            }
        } catch (Exception e) {
            System.out.println("testSiteCmdNormal failed with exception: " + e);
        }
    }

    private static void testSiteCmdNull() {
        try {
            FtpClient client = new FtpClient();
            client.siteCmd(null);
            System.out.println("testSiteCmdNull failed: no exception thrown for null");
        } catch (NullPointerException e) {
            System.out.println("testSiteCmdNull passed");
        } catch (Exception e) {
            System.out.println("testSiteCmdNull failed with unexpected exception: " + e);
        }
    }

    private static void testSiteCmdEmpty() {
        try {
            FtpClient client = new FtpClient();
            FtpClient result = client.siteCmd("");
            if (result != null) {
                System.out.println("testSiteCmdEmpty passed");
            } else {
                System.out.println("testSiteCmdEmpty failed: result is null");
            }
        } catch (Exception e) {
            System.out.println("testSiteCmdEmpty failed with exception: " + e);
        }
    }
}
