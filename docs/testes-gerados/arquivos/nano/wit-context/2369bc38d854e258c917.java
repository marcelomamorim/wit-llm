/* @test    @summary Test for getHandshakeApplicationProtocolSelector method in SSLSocket
 * @run main SSLSocketWitupTest
 */

import javax.net.ssl.SSLSocket;

public class SSLSocketWitupTest {

    public static void main(String[] args) {
        testGetHandshakeApplicationProtocolSelectorThrows();
    }

    public static void testGetHandshakeApplicationProtocolSelectorThrows() {
        SSLSocket socket = new SSLSocket() {
            @Override
            public Object getHandshakeApplicationProtocolSelector() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            socket.getHandshakeApplicationProtocolSelector();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
