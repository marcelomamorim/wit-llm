/*
 * @test
 * @summary Verifies that the default ExtendedSSLSession.getRequestedServerNames() throws UnsupportedOperationException when not overridden
 * @run main ExtendedSSLSessionWitupTest
 */

import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

public class ExtendedSSLSessionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ExtendedSSLSession session = new ExtendedSSLSession() {
            public String[] getLocalSupportedSignatureAlgorithms() { return new String[0]; }
            public String[] getPeerSupportedSignatureAlgorithms() { return new String[0]; }
            public byte[] getId() { return new byte[0]; }
            public SSLSessionContext getSessionContext() { return null; }
            public long getCreationTime() { return 0L; }
            public long getLastAccessedTime() { return 0L; }
            public void invalidate() { }
            public boolean isValid() { return false; }
            public void putValue(String name, Object value) { }
            public Object getValue(String name) { return null; }
            public void removeValue(String name) { }
            public String[] getValueNames() { return new String[0]; }
            public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
                throw new SSLPeerUnverifiedException("not verified");
            }
            public Certificate[] getLocalCertificates() { return null; }
            @SuppressWarnings("deprecation")
            public javax.security.cert.X509Certificate[] getPeerCertificateChain()
                    throws SSLPeerUnverifiedException {
                throw new SSLPeerUnverifiedException("not verified");
            }
            public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                throw new SSLPeerUnverifiedException("not verified");
            }
            public Principal getLocalPrincipal() { return null; }
            public String getCipherSuite() { return "TLS_NULL_WITH_NULL_NULL"; }
            public String getProtocol() { return "NONE"; }
            public String getPeerHost() { return null; }
            public int getPeerPort() { return -1; }
            public int getPacketBufferSize() { return 0; }
            public int getApplicationBufferSize() { return 0; }
        };

        boolean thrown = false;
        try {
            List<SNIServerName> names = session.getRequestedServerNames();
            check(false, "expected UnsupportedOperationException, got: " + names);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        check(thrown, "UnsupportedOperationException was not thrown");
    }
}
