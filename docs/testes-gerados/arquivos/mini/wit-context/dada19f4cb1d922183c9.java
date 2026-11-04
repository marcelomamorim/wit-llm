/*
 * @test
 * @summary Test sendUrgentData method throws SocketException as expected
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.SocketException;
import sun.security.ssl.BaseSSLSocketImpl;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {};
        try {
            socket.sendUrgentData(0);
            throw new RuntimeException("Expected SocketException was not thrown");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not supported")) {
                throw new RuntimeException("SocketException message unexpected: " + msg);
            }
        }
    }
}
