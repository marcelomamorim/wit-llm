/*
 * @test
 * @summary Verify that BaseSSLSocketImpl.setOOBInline always throws SocketException through the public SSLSocket API
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket raw = factory.createSocket();
        try {
            SSLSocket socket = (SSLSocket) raw;
            testSetOOBInlineTrowsOnTrue(socket);
            testSetOOBInlineThrowsOnFalse(socket);
        } finally {
            raw.close();
        }
    }

    private static void testSetOOBInlineTrowsOnTrue(SSLSocket socket) {
        try {
            socket.setOOBInline(true);
            throw new RuntimeException("Expected SocketException for setOOBInline(true)");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ineffective")) {
                throw new RuntimeException("Unexpected message: " + msg, e);
            }
        }
    }

    private static void testSetOOBInlineThrowsOnFalse(SSLSocket socket) {
        try {
            socket.setOOBInline(false);
            throw new RuntimeException("Expected SocketException for setOOBInline(false)");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ineffective")) {
                throw new RuntimeException("Unexpected message: " + msg, e);
            }
        }
    }
}
