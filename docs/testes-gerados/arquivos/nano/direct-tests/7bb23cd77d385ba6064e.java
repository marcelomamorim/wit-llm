/* @test    @summary Test getSupportedExtensions method of KeyChecker class    @run main KeyCheckerWitupTest */

import java.util.*;
import sun.security.provider.certpath.KeyChecker;

public class KeyCheckerWitupTest {

    public static void main(String[] args) {
        testGetSupportedExtensionsReturnsSet();
        testGetSupportedExtensionsReturnsUnmodifiableSet();
    }

    private static void testGetSupportedExtensionsReturnsSet() {
        KeyChecker checker = new KeyChecker(5, null);
        Set<String> result = checker.getSupportedExtensions();
        if (result == null) {
            throw new AssertionError("Expected non-null set");
        }
        if (!(result instanceof Set)) {
            throw new AssertionError("Result is not a Set");
        }
        if (result.isEmpty()) {
            throw new AssertionError("Set should not be empty");
        }
        // Check that known extension strings are present
        boolean hasKeyUsage = false;
        boolean hasExtendedKeyUsage = false;
        boolean hasSubjectAltName = false;
        for (String ext : result) {
            if (ext.equals("KeyUsage")) {
                hasKeyUsage = true;
            } else if (ext.equals("ExtendedKeyUsage")) {
                hasExtendedKeyUsage = true;
            } else if (ext.equals("SubjectAlternativeName")) {
                hasSubjectAltName = true;
            }
        }
        if (!hasKeyUsage || !hasExtendedKeyUsage || !hasSubjectAltName) {
            throw new AssertionError("Set does not contain expected extension strings");
        }
    }

    private static void testGetSupportedExtensionsReturnsUnmodifiableSet() {
        KeyChecker checker = new KeyChecker(5, null);
        Set<String> result = checker.getSupportedExtensions();
        try {
            result.add("NewExtension");
            throw new AssertionError("Set should be unmodifiable, but add succeeded");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
