/*
 * @test
 * @summary Verify that sendUrgentData(int) on an SSLSocket always throws SocketException with the unsupported message
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        int[] values = {0, 1, 65, 255, -1};
        for (int v : values) {
            try (SSLSocket socket = (SSLSocket) factory.createSocket()) {
                checkUrgentDataUnsupported(socket, v);
            }
        }
    }

    private static void checkUrgentDataUnsupported(SSLSocket socket, int data) throws Exception {
        try {
            socket.sendUrgentData(data);
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not supported")) {
                throw new RuntimeException("Unexpected message for data " + data + ": " + msg, e);
            }
            return;
        }
        throw new RuntimeException("sendUrgentData(" + data + ") did not throw SocketException");
    }
}
