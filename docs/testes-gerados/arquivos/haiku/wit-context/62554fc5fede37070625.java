/*
 * @test
 * @summary Verify SSLSocketImpl.setEnabledProtocols rejects null and accepts a valid protocol array
 * @run main SSLSocketImplWitupTest
 */

import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

public class SSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLContext context = SSLContext.getDefault();
        try (SSLSocket socket = (SSLSocket) context.getSocketFactory().createSocket()) {
            testNullProtocolsThrowsIllegalArgumentException(socket);
            testValidProtocolIsApplied(socket);
        }
    }

    private static void testNullProtocolsThrowsIllegalArgumentException(SSLSocket socket) {
        try {
            socket.setEnabledProtocols(null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for null protocols");
    }

    private static void testValidProtocolIsApplied(SSLSocket socket) {
        socket.setEnabledProtocols(new String[] {"TLSv1.2"});
        String[] enabled = socket.getEnabledProtocols();
        if (enabled == null || !Arrays.asList(enabled).contains("TLSv1.2")) {
            throw new RuntimeException("TLSv1.2 not reported as enabled: "
                    + Arrays.toString(enabled));
        }
    }
}
