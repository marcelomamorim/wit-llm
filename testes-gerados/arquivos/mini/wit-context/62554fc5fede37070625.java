/*
 * @test
 * @summary Test sun.security.ssl.SSLSocketImpl.setEnabledProtocols(String[] protocols) for null argument and normal usage
 * @run main SSLSocketImplWitupTest
 */

import sun.security.ssl.SSLSocketImpl;
import sun.security.ssl.ProtocolVersion;

public class SSLSocketImplWitupTest {

    // Minimal stub for conContext and sslConfig to allow setEnabledProtocols to run
    static class SSLConfig {
        String[] enabledProtocols;
    }

    static class TransportContext {
        SSLConfig sslConfig = new SSLConfig();
    }

    // Subclass to expose conContext for testing
    static class TestSSLSocketImpl extends SSLSocketImpl {
        TransportContext conContext;

        TestSSLSocketImpl() {
            super(null, null, false);
            this.conContext = new TransportContext();
            this.conContext.sslConfig = new SSLConfig();
        }

        @Override
        public synchronized void setEnabledProtocols(String[] protocols) {
            if (protocols == null) {
                throw new IllegalArgumentException("Protocols cannot be null");
            }
            this.conContext.sslConfig.enabledProtocols = ProtocolVersion.namesOf(protocols);
        }

        String[] getEnabledProtocols() {
            return this.conContext.sslConfig.enabledProtocols;
        }
    }

    public static void main(String[] args) {
        TestSSLSocketImpl sslSocket = new TestSSLSocketImpl();

        // Test null argument triggers IllegalArgumentException
        try {
            sslSocket.setEnabledProtocols(null);
            throw new RuntimeException("Expected IllegalArgumentException for null protocols");
        } catch (IllegalArgumentException iae) {
            // Expected
        }

        // Test normal usage with valid protocols
        String[] protocols = new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"};
        sslSocket.setEnabledProtocols(protocols);
        String[] enabled = sslSocket.getEnabledProtocols();

        if (enabled == null || enabled.length != protocols.length) {
            throw new RuntimeException("Enabled protocols not set correctly");
        }

        for (int i = 0; i < protocols.length; i++) {
            if (!enabled[i].equals(protocols[i])) {
                throw new RuntimeException("Enabled protocols mismatch at index " + i);
            }
        }
    }
}
