/*
 * @test
 * @summary Test constructor of java.security.CodeSigner for null and non-null CertPath
 * @run main CodeSignerWitupTest
 */

import java.security.CodeSigner;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.CertificateFactory;
import java.io.ByteArrayInputStream;
import java.util.Collections;

public class CodeSignerWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorRejectsNullCertPath();
        testConstructorAcceptsValidCertPathAndNullTimestamp();
        testConstructorAcceptsValidCertPathAndTimestamp();
    }

    private static void testConstructorRejectsNullCertPath() {
        try {
            new CodeSigner(null, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorAcceptsValidCertPathAndNullTimestamp() throws Exception {
        CertPath cp = createEmptyCertPath();
        CodeSigner cs = new CodeSigner(cp, null);
        if (cs.getSignerCertPath() != cp) {
            throw new RuntimeException("SignerCertPath not set correctly");
        }
        if (cs.getTimestamp() != null) {
            throw new RuntimeException("Timestamp should be null");
        }
    }

    private static void testConstructorAcceptsValidCertPathAndTimestamp() throws Exception {
        CertPath cp = createEmptyCertPath();
        Timestamp ts = new Timestamp(System.currentTimeMillis(), null);
        CodeSigner cs = new CodeSigner(cp, ts);
        if (cs.getSignerCertPath() != cp) {
            throw new RuntimeException("SignerCertPath not set correctly");
        }
        if (cs.getTimestamp() != ts) {
            throw new RuntimeException("Timestamp not set correctly");
        }
    }

    private static CertPath createEmptyCertPath() throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // Create an empty CertPath by parsing empty byte array as PKCS7
        // This is a minimal valid CertPath instance for testing
        byte[] emptyPKCS7 = new byte[] {
            0x30, 0x0
        };
        return cf.generateCertPath(new ByteArrayInputStream(emptyPKCS7));
    }
}
