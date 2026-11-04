/*
 * @test
 * @summary Test JarVerifier.getCodeSource behavior with various inputs
 * @run main JarVerifierWitupTest
 */

import java.net.URL;
import java.security.CodeSource;
import java.util.jar.JarEntry;
import java.util.jar.JarVerifier;

public class JarVerifierWitupTest {

    public static void main(String[] args) throws Exception {
        // Create JarVerifier with empty manifest bytes
        JarVerifier verifier = new JarVerifier(new byte[0]);

        // Test 1: getCodeSource with null URL and null name
        CodeSource cs1 = verifier.getCodeSource(null, null);
        if (cs1 != null) {
            throw new RuntimeException("Expected null CodeSource for null URL and null name");
        }

        // Test 2: getCodeSource with valid URL and null name
        URL url = new URL("file:/tmp/test.jar");
        CodeSource cs2 = verifier.getCodeSource(url, null);
        if (cs2 != null) {
            throw new RuntimeException("Expected null CodeSource for valid URL and null name");
        }

        // Test 3: getCodeSource with valid URL and empty string name
        CodeSource cs3 = verifier.getCodeSource(url, "");
        if (cs3 != null) {
            throw new RuntimeException("Expected null CodeSource for valid URL and empty name");
        }

        // Test 4: getCodeSource with valid URL and non-existing name
        CodeSource cs4 = verifier.getCodeSource(url, "nonexistent-entry");
        if (cs4 != null) {
            throw new RuntimeException("Expected null CodeSource for valid URL and non-existing name");
        }

        // Test 5: getCodeSource with valid URL and name after adding a signer
        // We cannot add signers directly because signerMap() is not public,
        // so this test only verifies that no exception is thrown and returns null
        CodeSource cs5 = verifier.getCodeSource(url, "META-INF/MANIFEST.MF");
        if (cs5 != null) {
            throw new RuntimeException("Expected null CodeSource for valid URL and MANIFEST.MF name without signers");
        }

        System.out.println("All tests passed.");
    }
}
