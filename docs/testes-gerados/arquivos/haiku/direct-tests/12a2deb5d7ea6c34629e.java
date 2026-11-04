/*
 * @test
 * @summary Smoke test for the public FtpClient factory path that leads to the impl class containing run()
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        sun.net.ftp.FtpClient client = sun.net.ftp.FtpClient.create();
        if (client == null) {
            throw new RuntimeException("create() returned null");
        }
        if (!client.getClass().getName().equals("sun.net.ftp.impl.FtpClient")) {
            throw new RuntimeException("Unexpected implementation class: " + client.getClass().getName());
        }
        if (!(client instanceof sun.net.ftp.FtpClient)) {
            throw new RuntimeException("Instance is not a sun.net.ftp.FtpClient");
        }
    }
}
