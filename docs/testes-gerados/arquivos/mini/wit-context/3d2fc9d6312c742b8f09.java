/*
 * @test
 * @summary Test sun.security.ssl.BaseSSLSocketImpl.setOOBInline(boolean) throws SocketException as expected
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.SocketException;
import sun.security.ssl.BaseSSLSocketImpl;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {};
        try {
            socket.setOOBInline(true);
            throw new AssertionError("Expected SocketException not thrown");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("urgent data is not supported")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
