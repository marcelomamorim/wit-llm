/*
 * @test
 * @summary Test JarVerifier.getCodeSource(URL,String) for expected behavior and exception safety
 * @run main JarVerifierWitupTest
 */

import java.net.URL;
import java.security.CodeSource;
import java.util.jar.JarVerifier;

public class JarVerifierWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCodeSourceWithNullName();
        testGetCodeSourceWithEmptyName();
        testGetCodeSourceWithValidName();
    }

    private static void testGetCodeSourceWithNullName() throws Exception {
        // Construct JarVerifier with empty manifest bytes
        JarVerifier verifier = new JarVerifier(new byte[0]);
        URL url = new URL("file:/tmp/dummy.jar");

        // Passing null as name should not throw, but return a CodeSource (likely null or with no signers)
        CodeSource cs = verifier.getCodeSource(url, null);
        if (cs == null) {
            throw new RuntimeException("getCodeSource returned null for null name");
        }
    }

    private static void testGetCodeSourceWithEmptyName() throws Exception {
        JarVerifier verifier = new JarVerifier(new byte[0]);
        URL url = new URL("file:/tmp/dummy.jar");

        // Passing empty string as name
        CodeSource cs = verifier.getCodeSource(url, "");
        if (cs == null) {
            throw new RuntimeException("getCodeSource returned null for empty name");
        }
    }

    private static void testGetCodeSourceWithValidName() throws Exception {
        JarVerifier verifier = new JarVerifier(new byte[0]);
        URL url = new URL("file:/tmp/dummy.jar");

        // Use a valid name string
        String name = "META-INF/MANIFEST.MF";
        CodeSource cs = verifier.getCodeSource(url, name);
        if (cs == null) {
            throw new RuntimeException("getCodeSource returned null for valid name");
        }
    }
}
