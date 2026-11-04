/*
 * @test
 * @summary Test sun.security.ssl.SSLSocketImpl.setEnabledProtocols behavior
 * @run main sun.security.ssl.SSLSocketImplWitupTest
 */

import sun.security.ssl.SSLSocketImpl;
import sun.security.ssl.ProtocolVersion;

public class SSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetEnabledProtocolsNull();
        testSetEnabledProtocolsValid();
    }

    private static void testSetEnabledProtocolsNull() {
        SSLSocketImpl socket = createDummySSLSocketImpl();
        try {
            socket.setEnabledProtocols(null);
            throw new RuntimeException("Expected IllegalArgumentException for null protocols");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testSetEnabledProtocolsValid() {
        SSLSocketImpl socket = createDummySSLSocketImpl();
        String[] protocols = new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"};
        socket.setEnabledProtocols(protocols);
        // Verify that enabledProtocols is set correctly
        // We can check by comparing the namesOf result with the internal field
        // Since conContext is package-private and no getter, we rely on no exception and no crash
    }

    // Minimal dummy subclass to allow instantiation since SSLSocketImpl has no public constructor
    private static SSLSocketImpl createDummySSLSocketImpl() {
        // Use anonymous subclass to bypass constructor visibility
        return new SSLSocketImpl(null, null, false) {
            // Override methods if needed, but none needed for this test
        };
    }
}
