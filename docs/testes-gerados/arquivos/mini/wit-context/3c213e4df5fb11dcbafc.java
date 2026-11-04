/*
 * @test
 * @summary Test sun.security.provider.certpath.AlgorithmChecker.init(boolean) method behavior
 * @run main AlgorithmCheckerWitupTest
 */

import sun.security.provider.certpath.AlgorithmChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.CertPathValidatorException;
import java.util.Date;

public class AlgorithmCheckerWitupTest {
    public static void main(String[] args) throws Exception {
        testInitWithForwardFalse();
        testInitWithForwardTrueThrows();
        System.out.println("All tests passed.");
    }

    private static void testInitWithForwardFalse() throws Exception {
        // Create AlgorithmChecker with minimal constructor
        TrustAnchor anchor = new TrustAnchor(new DummyX509Certificate(), null);
        AlgorithmChecker checker = new AlgorithmChecker(anchor, "variant");

        // Call init with forward = false should not throw
        checker.init(false);
        // No exception means pass
    }

    private static void testInitWithForwardTrueThrows() {
        try {
            TrustAnchor anchor = new TrustAnchor(new DummyX509Certificate(), null);
            AlgorithmChecker checker = new AlgorithmChecker(anchor, "variant");
            checker.init(true);
            throw new RuntimeException("Expected CertPathValidatorException not thrown");
        } catch (CertPathValidatorException e) {
            String msg = e.getMessage();
            if (!"forward checking not supported".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
            // Expected exception caught
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }

    // Minimal dummy X509Certificate implementation to satisfy TrustAnchor constructor
    private static class DummyX509Certificate extends java.security.cert.X509Certificate {
        public void checkValidity() { }
        public void checkValidity(Date date) { }
        public int getVersion() { return 3; }
        public BigInteger getSerialNumber() { return BigInteger.ONE; }
        public java.security.Principal getIssuerDN() { return null; }
        public java.security.Principal getSubjectDN() { return null; }
        public Date getNotBefore() { return new Date(0); }
        public Date getNotAfter() { return new Date(Long.MAX_VALUE); }
        public byte[] getTBSCertificate() { return new byte[0]; }
        public byte[] getSignature() { return new byte[0]; }
        public String getSigAlgName() { return "NONE"; }
        public String getSigAlgOID() { return "0.0"; }
        public byte[] getSigAlgParams() { return null; }
        public boolean[] getIssuerUniqueID() { return null; }
        public boolean[] getSubjectUniqueID() { return null; }
        public boolean[] getKeyUsage() { return null; }
        public int getBasicConstraints() { return -1; }
        public byte[] getEncoded() { return new byte[0]; }
        public void verify(java.security.PublicKey key) { }
        public void verify(java.security.PublicKey key, String sigProvider) { }
        public String toString() { return "DummyX509Certificate"; }
        public java.security.PublicKey getPublicKey() { return null; }
    }
}
