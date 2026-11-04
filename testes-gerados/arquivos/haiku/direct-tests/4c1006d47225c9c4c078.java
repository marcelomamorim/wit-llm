/*
 * @test
 * @summary Verify that getOOBInline() on an SSLSocket created via the public SSLSocketFactory throws SocketException
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLSocketFactory;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket socket = factory.createSocket();
        try {
            boolean threw = false;
            try {
                socket.getOOBInline();
            } catch (SocketException e) {
                threw = true;
                String msg = e.getMessage();
                if (msg == null || !msg.contains("ineffective")) {
                    throw new RuntimeException("Unexpected message: " + msg);
                }
            }
            if (!threw) {
                throw new RuntimeException("getOOBInline() did not throw SocketException");
            }

            threw = false;
            try {
                socket.getOOBInline();
            } catch (SocketException e) {
                threw = true;
            }
            if (!threw) {
                throw new RuntimeException("Second getOOBInline() call did not throw SocketException");
            }
        } finally {
            socket.close();
        }
    }
}
