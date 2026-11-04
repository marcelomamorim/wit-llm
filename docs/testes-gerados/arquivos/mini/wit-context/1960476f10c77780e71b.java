/*
 * @test
 * @summary Basic deterministic test for JarVerifier constructor
 * @run main JarVerifierWitupTest
 */

import java.util.jar.JarVerifier;

public class JarVerifierWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with null byte array
        JarVerifier verifierNull = new JarVerifier(null);
        if (verifierNull == null) {
            throw new RuntimeException("JarVerifier constructor returned null with null input");
        }

        // Test with empty byte array
        JarVerifier verifierEmpty = new JarVerifier(new byte[0]);
        if (verifierEmpty == null) {
            throw new RuntimeException("JarVerifier constructor returned null with empty byte array");
        }

        // Test with non-empty byte array
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        JarVerifier verifierData = new JarVerifier(data);
        if (verifierData == null) {
            throw new RuntimeException("JarVerifier constructor returned null with non-empty byte array");
        }
    }
}
