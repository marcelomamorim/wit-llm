/*
 * @test
 * @summary Test the noop() method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientWitupTest
 */

import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testNoopSuccess();
        testNoopThrowsFtpProtocolException();
    }

    private static void testNoopSuccess() throws Exception {
        FtpClient client = createMockFtpClient();
        boolean commandIssued = false;
        client = new FtpClient() {
            @Override
            public sun.net.ftp.FtpClient issueCommandCheck(String command) throws IOException {
                if ("NOOP".equals(command)) {
                    commandIssued = true;
                }
                return this;
            }
        };
        FtpClient result = client.noop();
        assert result == client : "Expected the same client instance";
        assert commandIssued : "NOOP command was not issued";
    }

    private static void testNoopThrowsFtpProtocolException() throws Exception {
        FtpClient client = new FtpClient() {
            @Override
            public sun.net.ftp.FtpClient issueCommandCheck(String command) throws IOException {
                if ("NOOP".equals(command)) {
                    throw new sun.net.ftp.FtpProtocolException("Protocol error") {
                        private static final long serialVersionUID = 1L;
                    };
                }
                return this;
            }
        };
        try {
            client.noop();
            assert false : "Expected FtpProtocolException to be thrown";
        } catch (sun.net.ftp.FtpProtocolException e) {
            assert "Protocol error".equals(e.getMessage()) : "Unexpected exception message";
        }
    }

    private static FtpClient createMockFtpClient() {
        return new FtpClient();
    }
}
