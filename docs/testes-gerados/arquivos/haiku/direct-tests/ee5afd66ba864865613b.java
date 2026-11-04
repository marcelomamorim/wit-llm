/*
 * @test
 * @summary Verify that the base SSLEngine.getHandshakeSession() throws UnsupportedOperationException
 * @run main SSLEngineWitupTest
 */

import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLSession;

public class SSLEngineWitupTest {

    static class StubEngine extends SSLEngine {
        StubEngine() {
            super();
        }

        StubEngine(String host, int port) {
            super(host, port);
        }

        public SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) {
            throw new UnsupportedOperationException();
        }

        public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        public Runnable getDelegatedTask() {
            return null;
        }

        public void closeInbound() {
        }

        public boolean isInboundDone() {
            return false;
        }

        public void closeOutbound() {
        }

        public boolean isOutboundDone() {
            return false;
        }

        public String[] getSupportedCipherSuites() {
            return new String[0];
        }

        public String[] getEnabledCipherSuites() {
            return new String[0];
        }

        public void setEnabledCipherSuites(String[] suites) {
        }

        public String[] getSupportedProtocols() {
            return new String[0];
        }

        public String[] getEnabledProtocols() {
            return new String[0];
        }

        public void setEnabledProtocols(String[] protocols) {
        }

        public SSLSession getSession() {
            return null;
        }

        public void beginHandshake() {
        }

        public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
            return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        }

        public void setUseClientMode(boolean mode) {
        }

        public boolean getUseClientMode() {
            return false;
        }

        public void setNeedClientAuth(boolean need) {
        }

        public boolean getNeedClientAuth() {
            return false;
        }

        public void setWantClientAuth(boolean want) {
        }

        public boolean getWantClientAuth() {
            return false;
        }

        public void setEnableSessionCreation(boolean flag) {
        }

        public boolean getEnableSessionCreation() {
            return true;
        }
    }

    static void checkUnsupported(SSLEngine engine, String label) {
        boolean thrown = false;
        try {
            engine.getHandshakeSession();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from " + label);
        }
    }

    public static void main(String[] args) {
        checkUnsupported(new StubEngine(), "StubEngine()");
        checkUnsupported(new StubEngine("localhost", 443), "StubEngine(host, port)");
    }
}
