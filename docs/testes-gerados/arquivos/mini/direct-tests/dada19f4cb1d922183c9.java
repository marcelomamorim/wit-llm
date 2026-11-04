/*
 * @test
 * @summary Test that sendUrgentData throws SocketException as expected
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.SocketException;
import sun.security.ssl.BaseSSLSocketImpl;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {
            // Abstract class requires implementation of abstract methods
            // Provide minimal stubs to allow instantiation
            @Override
            public void startHandshake() {}
            @Override
            public void setUseClientMode(boolean mode) {}
            @Override
            public boolean getUseClientMode() { return false; }
            @Override
            public void setNeedClientAuth(boolean need) {}
            @Override
            public boolean getNeedClientAuth() { return false; }
            @Override
            public void setWantClientAuth(boolean want) {}
            @Override
            public boolean getWantClientAuth() { return false; }
            @Override
            public void setEnableSessionCreation(boolean flag) {}
            @Override
            public boolean getEnableSessionCreation() { return false; }
            @Override
            public String[] getSupportedCipherSuites() { return new String[0]; }
            @Override
            public String[] getEnabledCipherSuites() { return new String[0]; }
            @Override
            public void setEnabledCipherSuites(String[] suites) {}
            @Override
            public String[] getSupportedProtocols() { return new String[0]; }
            @Override
            public String[] getEnabledProtocols() { return new String[0]; }
            @Override
            public void setEnabledProtocols(String[] protocols) {}
            @Override
            public SSLSession getSession() { return null; }
            @Override
            public void addHandshakeCompletedListener(javax.net.ssl.HandshakeCompletedListener listener) {}
            @Override
            public void removeHandshakeCompletedListener(javax.net.ssl.HandshakeCompletedListener listener) {}
        };

        try {
            socket.sendUrgentData(0xFF);
            throw new RuntimeException("Expected SocketException was not thrown");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
