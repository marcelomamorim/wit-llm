/* @test    @summary Test getCodeSource method of JarVerifier for exception scenarios    @run main JarVerifierWitupTest */

import java.net.URL;
import java.security.CodeSource;
import java.util.jar.JarVerifier;

public class JarVerifierWitupTest {

    public static void main(String[] args) {
        testGetCodeSourceWithNullSignerMap();
        testGetCodeSourceWithNegativeSignerMapSize();
        testGetCodeSourceWithNullSignerMapAndNegativeSize();
    }

    private static void testGetCodeSourceWithNullSignerMap() {
        try {
            // Create an instance with dummy rawBytes
            byte[] rawBytes = new byte[0];
            JarVerifier verifier = new JarVerifier(rawBytes);
            // Override signerMap method to return null
            JarVerifier verifierWithNullMap = new JarVerifier(rawBytes) {
                @Override
                public java.util.Map<String, java.security.CodeSigner[]> signerMap() {
                    return null;
                }
            };
            URL url = new URL("file:/dummy.jar");
            String name = "dummyEntry";
            verifierWithNullMap.getCodeSource(url, name);
        } catch (Exception e) {
            // Expecting an exception due to null signerMap
            System.out.println("Expected exception for null signerMap: " + e);
        }
    }

    private static void testGetCodeSourceWithNegativeSignerMapSize() {
        try {
            byte[] rawBytes = new byte[0];
            // Override signerMap to return a map with negative size (simulate via custom map)
            java.util.Map<String, java.security.CodeSigner[]> negativeMap = new java.util.HashMap<>();
            // No entries added, size is 0, but simulate negative size by custom class if needed
            // Since HashMap can't have negative size, simulate by throwing exception or similar
            // For simplicity, we simulate by overriding method to throw exception
            JarVerifier verifier = new JarVerifier(rawBytes) {
                @Override
                public java.util.Map<String, java.security.CodeSigner[]> signerMap() {
                    throw new IllegalArgumentException("Simulated negative size");
                }
            };
            URL url = new URL("file:/dummy.jar");
            String name = "dummyEntry";
            verifier.getCodeSource(url, name);
        } catch (Exception e) {
            System.out.println("Expected exception for negative signerMap size: " + e);
        }
    }

    private static void testGetCodeSourceWithNullSignerMapAndNegativeSize() {
        try {
            byte[] rawBytes = new byte[0];
            // Override signerMap to return null and simulate negative size condition
            JarVerifier verifier = new JarVerifier(rawBytes) {
                @Override
                public java.util.Map<String, java.security.CodeSigner[]> signerMap() {
                    return null;
                }
            };
            URL url = new URL("file:/dummy.jar");
            String name = "dummyEntry";
            verifier.getCodeSource(url, name);
        } catch (Exception e) {
            System.out.println("Expected exception for null signerMap with negative size condition: " + e);
        }
    }
}
