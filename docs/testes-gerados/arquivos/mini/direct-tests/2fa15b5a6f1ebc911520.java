/*
 * @test
 * @summary Test ExtendedSSLSession.getRequestedServerNames throws UnsupportedOperationException
 * @run main ExtendedSSLSessionWitupTest
 */

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIServerName;
import java.util.List;

public class ExtendedSSLSessionWitupTest {
    public static void main(String[] args) {
        ExtendedSSLSession session = new ExtendedSSLSession() {
            @Override
            public String[] getLocalSupportedSignatureAlgorithms() {
                return new String[0];
            }

            @Override
            public String[] getPeerSupportedSignatureAlgorithms() {
                return new String[0];
            }

            @Override
            public byte[] getId() {
                return new byte[0];
            }

            @Override
            public SSLSessionContext getSessionContext() {
                return null;
            }

            @Override
            public long getCreationTime() {
                return 0L;
            }

            @Override
            public long getLastAccessedTime() {
                return 0L;
            }

            @Override
            public void invalidate() {
            }

            @Override
            public boolean isValid() {
                return false;
            }

            @Override
            public void putValue(String name, Object value) {
            }

            @Override
            public Object getValue(String name) {
                return null;
            }

            @Override
            public void removeValue(String name) {
            }

            @Override
            public String[] getValueNames() {
                return new String[0];
            }

            @Override
            public Certificate[] getPeerCertificates() {
                return new Certificate[0];
            }

            @Override
            public Certificate[] getLocalCertificates() {
                return new Certificate[0];
            }

            @Override
            public javax.security.cert.X509Certificate[] getPeerCertificateChain() {
                return new javax.security.cert.X509Certificate[0];
            }

            @Override
            public Principal getPeerPrincipal() {
                return null;
            }

            @Override
            public Principal getLocalPrincipal() {
                return null;
            }

            @Override
            public String getCipherSuite() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getPeerHost() {
                return null;
            }

            @Override
            public int getPeerPort() {
                return 0;
            }

            @Override
            public int getPacketBufferSize() {
                return 0;
            }

            @Override
            public int getApplicationBufferSize() {
                return 0;
            }
        };

        try {
            session.getRequestedServerNames();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
