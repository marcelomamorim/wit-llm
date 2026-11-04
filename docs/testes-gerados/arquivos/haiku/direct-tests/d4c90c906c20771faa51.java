/*
 * @test
 * @summary Verify makeDirectory on an unconnected FtpClient does not silently succeed
 * @modules java.base/sun.net.ftp
 * @modules java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.IOException;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        if (client == null) {
            throw new RuntimeException("create() returned null");
        }

        boolean failed = false;
        try {
            client.makeDirectory("witup_dir");
        } catch (FtpProtocolException | IOException | RuntimeException e) {
            failed = true;
        }
        if (!failed) {
            throw new RuntimeException("makeDirectory on unconnected client did not fail");
        }
    }
}
