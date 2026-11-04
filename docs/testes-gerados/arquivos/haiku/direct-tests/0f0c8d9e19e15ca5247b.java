/*
 * @test
 * @summary Verify that the base SSLSocket.getHandshakeSession() throws UnsupportedOperationException
 * @run main SSLSocketWitupTest
 */

import java.io.IOException;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class SSLSocketWitupTest {

    static class StubSocket extends SSLSocket {
        StubSocket() {
            super();
        }

        @Override
        public String[] getSupportedCipherSuites() { throw new UnsupportedOperationException(); }
        @Override
        public String[] getEnabledCipherSuites() { throw new UnsupportedOperationException(); }
        @Override
        public void setEnabledCipherSuites(String[] suites) { throw new UnsupportedOperationException(); }
        @Override
        public String[] getSupportedProtocols() { throw new UnsupportedOperationException(); }
        @Override
        public String[] getEnabledProtocols() { throw new UnsupportedOperationException(); }
        @Override
        public void setEnabledProtocols(String[] protocols) { throw new UnsupportedOperationException(); }
        @Override
        public SSLSession getSession() { throw new UnsupportedOperationException(); }
        @Override
        public void addHandshakeCompletedListener(HandshakeCompletedListener listener) { throw new UnsupportedOperationException(); }
        @Override
        public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) { throw new UnsupportedOperationException(); }
        @Override
        public void startHandshake() throws IOException { throw new UnsupportedOperationException(); }
        @Override
        public void setUseClientMode(boolean mode) { throw new UnsupportedOperationException(); }
        @Override
        public boolean getUseClientMode() { throw new UnsupportedOperationException(); }
        @Override
        public void setNeedClientAuth(boolean need) { throw new UnsupportedOperationException(); }
        @Override
        public boolean getNeedClientAuth() { throw new UnsupportedOperationException(); }
        @Override
        public void setWantClientAuth(boolean want) { throw new UnsupportedOperationException(); }
        @Override
        public boolean getWantClientAuth() { throw new UnsupportedOperationException(); }
        @Override
        public void setEnableSessionCreation(boolean flag) { throw new UnsupportedOperationException(); }
        @Override
        public boolean getEnableSessionCreation() { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) throws Exception {
        StubSocket socket = new StubSocket();
        boolean thrown = false;
        try {
            socket.getHandshakeSession();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from getHandshakeSession()");
        }
    }
}
