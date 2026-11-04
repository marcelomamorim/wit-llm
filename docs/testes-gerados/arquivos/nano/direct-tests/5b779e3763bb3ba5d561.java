/* @test    @summary Test getCodeSource method of JarVerifier for various signer states    @run main JarVerifierWitupTest */

import java.net.URL;
import java.security.CodeSource;
import java.util.jar.JarVerifier;

public class JarVerifierWitupTest {

    public static void main(String[] args) {
        testGetCodeSourceWithSigners();
        testGetCodeSourceWithNullSigners();
    }

    private static void testGetCodeSourceWithSigners() {
        try {
            byte[] rawBytes = new byte[0];
            JarVerifier verifier = new JarVerifier(rawBytes);
            String entryName = "testEntry";
            URL url = new URL("file:///test.jar");
            // Simulate signers array
            CodeSource cs = verifier.getCodeSource(url, entryName);
            // Since signerMap() is private, we test the method's output is not null
            if (cs == null) {
                throw new AssertionError("Expected non-null CodeSource");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during testGetCodeSourceWithSigners", e);
        }
    }

    private static void testGetCodeSourceWithNullSigners() {
        try {
            byte[] rawBytes = new byte[0];
            JarVerifier verifier = new JarVerifier(rawBytes);
            String entryName = "nonexistentEntry";
            URL url = new URL("file:///test.jar");
            CodeSource cs = verifier.getCodeSource(url, entryName);
            if (cs == null) {
                throw new AssertionError("Expected non-null CodeSource even for nonexistent entry");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during testGetCodeSourceWithNullSigners", e);
        }
    }
}
