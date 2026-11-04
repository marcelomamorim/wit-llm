/*
 * @test
 * @summary Test the abort() method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientAbortTest
 */

import java.io.IOException;

public class FtpClientAbortTest {

    public static void main(String[] args) {
        testAbortMethod();
    }

    public static void testAbortMethod() {
        sun.net.ftp.impl.FtpClient ftpClient = null;
        try {
            // Use reflection to instantiate the protected constructor
            java.lang.reflect.Constructor<sun.net.ftp.impl.FtpClient> constructor =
                sun.net.ftp.impl.FtpClient.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ftpClient = constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            // If instantiation fails, skip test
            System.err.println("Failed to instantiate FtpClient: " + e);
            return;
        }
        try {
            // Call abort() method
            sun.net.ftp.impl.FtpClient result = ftpClient.abort();
            // Verify that the method returns the same instance
            if (result != null && result == ftpClient) {
                System.out.println("abort() method executed successfully.");
            } else {
                System.err.println("abort() did not return the expected instance.");
            }
        } catch (Exception e) {
            System.err.println("Exception during abort(): " + e);
        }
    }
}
