/*
 * @test
 * @summary Verifies that getOOBInline() on an SSLSocket always throws SocketException
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class BaseSSLSocketImplWitupTest {
    public static void main(String[] args) throws Exception {
        SocketFactory factory = SSLSocketFactory.getDefault();
        Socket socket = factory.createSocket();
        try {
            try {
                socket.getOOBInline();
                throw new AssertionError("expected SocketException from getOOBInline()");
            } catch (SocketException expected) {
                String msg = expected.getMessage();
                if (msg == null || !msg.contains("ineffective")) {
                    throw new AssertionError("unexpected message: " + msg);
                }
            }
        } finally {
            socket.close();
        }
    }
}
