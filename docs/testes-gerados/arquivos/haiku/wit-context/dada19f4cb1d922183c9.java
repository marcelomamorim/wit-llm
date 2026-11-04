/*
 * @test
 * @summary Verify that sendUrgentData(int) on an SSLSocket always throws SocketException
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLSocketFactory;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (Socket socket = factory.createSocket()) {
            int[] values = {0, 1, 65, -1};
            for (int data : values) {
                boolean thrown = false;
                try {
                    socket.sendUrgentData(data);
                } catch (SocketException e) {
                    thrown = true;
                    String msg = e.getMessage();
                    if (msg == null || !msg.contains("not supported")) {
                        throw new RuntimeException("Unexpected message: " + msg);
                    }
                }
                if (!thrown) {
                    throw new RuntimeException("Expected SocketException for data " + data);
                }
            }
        }
    }
}
