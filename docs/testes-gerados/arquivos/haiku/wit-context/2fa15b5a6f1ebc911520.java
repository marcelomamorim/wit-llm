/*
 * @test
 * @summary Verifies that ExtendedSSLSession.getRequestedServerNames() throws UnsupportedOperationException in the default implementation
 * @run main ExtendedSSLSessionWitupTest
 */

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SSLSessionContext;

public class ExtendedSSLSessionWitupTest {

    static final class StubSession extends ExtendedSSLSession {
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
        public Certificate[] getPeerCertificates() { return null; }
        public Certificate[] getLocalCertificates() { return null; }
        public X509Certificate[] getPeerCertificateChain() { return null; }
        public Principal getPeerPrincipal() { return null; }
        public Principal getLocalPrincipal() { return null; }
        public String getCipherSuite() { return ""; }
        public String getProtocol() { return ""; }
        public String getPeerHost() { return null; }
        public int getPeerPort() { return -1; }
        public int getPacketBufferSize() { return 0; }
        public int getApplicationBufferSize() { return 0; }
    }

    public static void main(String[] args) throws Exception {
        ExtendedSSLSession session = new StubSession();
        boolean thrown = false;
        try {
            session.getRequestedServerNames();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from getRequestedServerNames()");
        }
    }
}
