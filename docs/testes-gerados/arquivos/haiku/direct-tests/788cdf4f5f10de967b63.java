/*
 * @test
 * @summary Verify that SSLSocketImpl output stream write(int) on an unconnected socket fails only with IOException
 * @run main SSLSocketImplWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket socket = (SSLSocket) factory.createSocket()) {
            try {
                OutputStream out = socket.getOutputStream();
                out.write(0x41);
            } catch (IOException expected) {
                // Acceptable: the socket is not connected or handshaked.
            }
        }
    }
}
