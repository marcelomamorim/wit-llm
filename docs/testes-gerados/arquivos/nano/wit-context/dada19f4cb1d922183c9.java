/*
 * @test
 * @summary Test for sendUrgentData method in BaseSSLSocketImpl.
 * @run main BaseSSLSocketImplWitupTest
 */

import sun.security.ssl.BaseSSLSocketImpl;
import java.net.SocketException;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) {
        testSendUrgentDataThrowsSocketException();
    }

    public static void testSendUrgentDataThrowsSocketException() {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {
            // Anonymous subclass to instantiate abstract class
        };
        int testData = 0;
        try {
            socket.sendUrgentData(testData);
            System.out.println("FAIL: Expected SocketException was not thrown.");
        } catch (SocketException e) {
            String message = e.getMessage();
            if (message != null && message.contains("not supported")) {
                System.out.println("PASS: SocketException thrown as expected.");
            } else {
                System.out.println("FAIL: SocketException thrown, but message was unexpected: " + message);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
