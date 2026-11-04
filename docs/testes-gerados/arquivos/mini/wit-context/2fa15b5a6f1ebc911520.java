/*
 * @test
 * @summary Test ExtendedSSLSession.getRequestedServerNames() throws UnsupportedOperationException
 * @run main ExtendedSSLSessionWitupTest
 */

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIServerName;
import java.util.List;

public class ExtendedSSLSessionWitupTest {
    public static void main(String[] args) {
        ExtendedSSLSession session = new ExtendedSSLSession() {
            public String[] getLocalSupportedSignatureAlgorithms() { return new String[0]; }
            public String[] getPeerSupportedSignatureAlgorithms() { return new String[0]; }
            public List<SNIServerName> getRequestedServerNames() {
                // Use the default implementation which throws UnsupportedOperationException
                return ExtendedSSLSession.super.getRequestedServerNames();
            }
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
            public javax.security.cert.X509Certificate[] getPeerCertificateChain() { return null; }
            public Principal getPeerPrincipal() { return null; }
            public Principal getLocalPrincipal() { return null; }
            public String getCipherSuite() { return null; }
            public String getProtocol() { return null; }
            public String getPeerHost() { return null; }
            public int getPeerPort() { return 0; }
            public int getPacketBufferSize() { return 0; }
            public int getApplicationBufferSize() { return 0; }
        };

        try {
            session.getRequestedServerNames();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
