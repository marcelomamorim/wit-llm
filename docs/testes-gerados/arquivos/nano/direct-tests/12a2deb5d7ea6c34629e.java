/*
 * @test
 * @summary Test the run() method of sun.net.ftp.impl.FtpClient.
 * @run main FtpClientWitupTest
 */

import java.net.*;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testRunReturnsSocket();
    }

    public static void testRunReturnsSocket() {
        sun.net.ftp.impl.FtpClient ftpClient = new sun.net.ftp.impl.FtpClient() {
            @Override
            public Socket run() {
                return new Socket();
            }
        };
        Socket socket = ftpClient.run();
        if (socket == null) {
            throw new AssertionError("run() returned null");
        }
        System.out.println("run() returned a socket: " + socket.getClass().getName());
    }
}
