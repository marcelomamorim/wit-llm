/*
 * @test
 * @summary Test constructor of CodeSigner for null and non-null CertPath
 * @run main CodeSignerWitupTest
 */

import java.security.CodeSigner;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.util.Collections;
import java.util.List;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateException;
import java.io.ByteArrayInputStream;

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
            // expected
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

    private static CertPath createEmptyCertPath() throws CertificateException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // Create an empty CertPath by passing empty list
        List emptyList = Collections.emptyList();
        return cf.generateCertPath(emptyList);
    }
}
