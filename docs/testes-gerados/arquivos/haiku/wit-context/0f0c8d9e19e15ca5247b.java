/*
 * @test
 * @summary Verify that SSLSocket.getHandshakeSession() throws UnsupportedOperationException in the current checkout
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

        public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
        }

        public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
        }

        public void startHandshake() throws IOException {
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
            throw new RuntimeException("Expected UnsupportedOperationException from getHandshakeSession()");
        }
    }
}
