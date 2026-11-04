/*
 * @test
 * @summary Verify SSLSocketImpl.setEnabledProtocols rejects null and stores enabled protocol names through the public SSLSocket API
 * @run main SSLSocketImplWitupTest
 */

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = SSLContext.getDefault().getSocketFactory();
        try (SSLSocket socket = (SSLSocket) factory.createSocket()) {
            boolean iaeThrown = false;
            try {
                socket.setEnabledProtocols(null);
            } catch (IllegalArgumentException expected) {
                iaeThrown = true;
            }
            if (!iaeThrown) {
                throw new RuntimeException("Expected IllegalArgumentException for null protocols");
            }

            socket.setEnabledProtocols(new String[] {"TLSv1.2"});
            String[] enabled = socket.getEnabledProtocols();
            if (enabled.length != 1 || !"TLSv1.2".equals(enabled[0])) {
                throw new RuntimeException("Unexpected enabled protocols after set: "
                        + java.util.Arrays.toString(enabled));
            }
        }
    }
}
