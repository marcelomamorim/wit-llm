/*
 * @test
 * @summary Test that setOOBInline always throws SocketException
 * @run main BaseSSLSocketImplWitupTest
 */

import sun.security.ssl.BaseSSLSocketImpl;
import java.net.SocketException;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {
            // Provide minimal implementation to allow instantiation
        };

        try {
            socket.setOOBInline(true);
            throw new RuntimeException("Expected SocketException not thrown");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ineffective")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }

        try {
            socket.setOOBInline(false);
            throw new RuntimeException("Expected SocketException not thrown");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ineffective")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
