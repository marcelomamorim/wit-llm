/*
 * @test
 * @summary Test sun.security.provider.certpath.KeyChecker.getSupportedExtensions() for correct supported extensions set
 * @run main KeyCheckerWitupTest
 */

import java.util.Set;
import java.util.Iterator;
import sun.security.provider.certpath.KeyChecker;
import sun.security.x509.PKIXExtensions;

public class KeyCheckerWitupTest {
    public static void main(String[] args) throws Exception {
        // Create KeyChecker instance with dummy parameters
        KeyChecker checker = new KeyChecker(1, null);

        // Call getSupportedExtensions multiple times to test caching and immutability
        Set<String> exts1 = checker.getSupportedExtensions();
        Set<String> exts2 = checker.getSupportedExtensions();

        if (exts1 == null || exts2 == null) {
            throw new RuntimeException("getSupportedExtensions() returned null");
        }

        if (exts1 != exts2) {
            throw new RuntimeException("getSupportedExtensions() should return the same Set instance on repeated calls");
        }

        // Check that the set contains exactly the expected OIDs
        String keyUsageOid = PKIXExtensions.KeyUsage_Id.toString();
        String extKeyUsageOid = PKIXExtensions.ExtendedKeyUsage_Id.toString();
        String subjectAltNameOid = PKIXExtensions.SubjectAlternativeName_Id.toString();

        if (!exts1.contains(keyUsageOid)) {
            throw new RuntimeException("Supported extensions missing KeyUsage OID");
        }
        if (!exts1.contains(extKeyUsageOid)) {
            throw new RuntimeException("Supported extensions missing ExtendedKeyUsage OID");
        }
        if (!exts1.contains(subjectAltNameOid)) {
            throw new RuntimeException("Supported extensions missing SubjectAlternativeName OID");
        }

        // Check that the set is unmodifiable by attempting to add an element and expecting UnsupportedOperationException
        boolean threw = false;
        try {
            exts1.add("1.2.3.4");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Supported extensions set should be unmodifiable");
        }

        // Check that the set size is exactly 3
        if (exts1.size() != 3) {
            throw new RuntimeException("Supported extensions set size should be 3");
        }

        // Check iteration over the set yields expected OIDs
        boolean foundKeyUsage = false;
        boolean foundExtKeyUsage = false;
        boolean foundSubjectAltName = false;
        Iterator<String> it = exts1.iterator();
        while (it.hasNext()) {
            String oid = it.next();
            if (oid.equals(keyUsageOid)) {
                foundKeyUsage = true;
            } else if (oid.equals(extKeyUsageOid)) {
                foundExtKeyUsage = true;
            } else if (oid.equals(subjectAltNameOid)) {
                foundSubjectAltName = true;
            } else {
                throw new RuntimeException("Unexpected OID in supported extensions: " + oid);
            }
        }

        if (!foundKeyUsage || !foundExtKeyUsage || !foundSubjectAltName) {
            throw new RuntimeException("Not all expected OIDs found in supported extensions");
        }

        System.out.println("KeyChecker.getSupportedExtensions() test passed.");
    }
}
