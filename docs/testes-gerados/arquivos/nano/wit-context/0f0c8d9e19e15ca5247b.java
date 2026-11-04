/* @test    @summary Test for getHandshakeSession method in SSLSocket.
 * @run main SSLSocketWitupTest
 */

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSession;

public class SSLSocketWitupTest {

    public static void main(String[] args) {
        testGetHandshakeSessionThrows();
    }

    public static void testGetHandshakeSessionThrows() {
        SSLSocket socket = new SSLSocket() {
            @Override
            public SSLSession getHandshakeSession() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            socket.getHandshakeSession();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected");
        } catch (Exception e) {
            System.out.println("Test failed: unexpected exception: " + e);
        }
    }
}
