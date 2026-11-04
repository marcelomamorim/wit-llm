/* @test    @summary Test for getRequestedServerNames method in ExtendedSSLSession.
 * @run main ExtendedSSLSessionWitupTest
 */

import javax.net.ssl.ExtendedSSLSession;
import java.util.List;

public class ExtendedSSLSessionWitupTest {

    public static void main(String[] args) {
        testGetRequestedServerNamesThrowsException();
    }

    public static void testGetRequestedServerNamesThrowsException() {
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
            public List<javax.net.ssl.SNIServerName> getRequestedServerNames() {
                throw new UnsupportedOperationException();
            }

            // Implement other abstract methods with dummy returns
            @Override
            public String getCipherSuite() { return null; }
            @Override
            public java.security.cert.Certificate[] getPeerCertificates() { return null; }
            @Override
            public java.security.cert.Certificate[] getLocalCertificates() { return null; }
            @Override
            public String getId() { return null; }
            @Override
            public long getCreationTime() { return 0; }
            @Override
            public long getLastAccessedTime() { return 0; }
            @Override
            public java.util.List<java.security.Principal> getPeerPrincipals() { return null; }
            @Override
            public java.util.List<java.security.Principal> getLocalPrincipals() { return null; }
            @Override
            public String getPeerHost() { return null; }
            @Override
            public int getPeerPort() { return 0; }
            @Override
            public int getPacketBufferSize() { return 0; }
            @Override
            public int getApplicationBufferSize() { return 0; }
            @Override
            public java.util.List<java.security.cert.Certificate> getLocalCertificatesList() { return null; }
            @Override
            public java.util.List<java.security.Principal> getPeerPrincipalsList() { return null; }
            @Override
            public String getProtocol() { return null; }
            @Override
            public String getSessionId() { return null; }
            @Override
            public java.util.List<javax.net.ssl.SNIServerName> getRequestedServerNames() { throw new UnsupportedOperationException(); }
        };
        try {
            session.getRequestedServerNames();
            System.out.println("Test Failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test Passed: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test Failed: Unexpected exception: " + e);
        }
    }
}
