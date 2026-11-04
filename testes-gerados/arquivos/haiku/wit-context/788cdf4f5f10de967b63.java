/*
 * @test
 * @summary Checks that writing through the output stream of a closed SSLSocket fails with IOException
 * @modules java.base/sun.security.ssl
 * @run main SSLSocketImplWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLSocketImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket();
        socket.close();
        try {
            OutputStream os = socket.getOutputStream();
            os.write(1);
            throw new RuntimeException("Expected IOException writing to a closed SSLSocket");
        } catch (IOException expected) {
        }
    }
}
