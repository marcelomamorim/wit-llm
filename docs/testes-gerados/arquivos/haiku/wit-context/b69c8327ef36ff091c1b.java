/*
 * @test
 * @summary Verifies CodeSigner constructor null-check and observable getters
 * @run main CodeSignerWitupTest
 */

import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertPath;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CodeSignerWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCertPathThrowsNPE();
        testNullTimestampIsPreserved();
        testCertPathIdentityIsPreserved();
    }

    static void testNullCertPathThrowsNPE() {
        boolean thrown = false;
        try {
            new CodeSigner(null, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null signerCertPath");
        }
    }

    static void testNullTimestampIsPreserved() {
        CertPath path = newCertPath();
        CodeSigner signer = new CodeSigner(path, null);
        if (signer.getTimestamp() != null) {
            throw new RuntimeException("Expected null timestamp to be returned as null");
        }
    }

    static void testCertPathIdentityIsPreserved() {
        CertPath path = newCertPath();
        CodeSigner signer = new CodeSigner(path, null);
        if (signer.getSignerCertPath() != path) {
            throw new RuntimeException("Expected the same CertPath instance to be returned");
        }
    }

    static CertPath newCertPath() {
        return new CertPath("X.509") {
            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<String> getEncodings() {
                return Collections.<String>emptyIterator();
            }

            @Override
            public byte[] getEncoded() throws CertificateEncodingException {
                return new byte[0];
            }

            @Override
            public byte[] getEncoded(String encoding) throws CertificateEncodingException {
                return new byte[0];
            }

            @Override
            public List<? extends Certificate> getCertificates() {
                return Collections.emptyList();
            }
        };
    }
}
