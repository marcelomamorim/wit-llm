/*
 * @test
 * @summary Test getSupportedExtensions method of KeyChecker
 * @run main KeyCheckerWitupTest
 */

import java.util.Set;
import sun.security.provider.certpath.KeyChecker;
import static sun.security.x509.PKIXExtensions.*;

public class KeyCheckerWitupTest {
    public static void main(String[] args) throws Exception {
        // Create KeyChecker with arbitrary certPathLen and null targetConstraints
        KeyChecker checker = new KeyChecker(1, null);

        // Call getSupportedExtensions first time
        Set<String> exts1 = checker.getSupportedExtensions();

        // Check that the returned set contains the expected extension OIDs
        if (!exts1.contains(KeyUsage_Id.toString())) {
            throw new RuntimeException("Missing KeyUsage extension");
        }
        if (!exts1.contains(ExtendedKeyUsage_Id.toString())) {
            throw new RuntimeException("Missing ExtendedKeyUsage extension");
        }
        if (!exts1.contains(SubjectAlternativeName_Id.toString())) {
            throw new RuntimeException("Missing SubjectAlternativeName extension");
        }

        // Check that the set is unmodifiable by attempting to add an element
        boolean unmodifiable = false;
        try {
            exts1.add("1.2.3.4");
        } catch (UnsupportedOperationException e) {
            unmodifiable = true;
        }
        if (!unmodifiable) {
            throw new RuntimeException("Returned set should be unmodifiable");
        }

        // Call getSupportedExtensions second time and check it returns the same set instance
        Set<String> exts2 = checker.getSupportedExtensions();
        if (exts1 != exts2) {
            throw new RuntimeException("getSupportedExtensions should return the same set instance on subsequent calls");
        }
    }
}
