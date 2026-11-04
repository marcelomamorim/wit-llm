/*
 * @test
 * @summary Test ManifestDigester.manifestDigest behavior including null rawBytes
 * @run main ManifestDigesterWitupTest
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.security.util.ManifestDigester;

public class ManifestDigesterWitupTest {

    public static void main(String[] args) throws Exception {
        testManifestDigestWithValidBytes();
        testManifestDigestWithNullRawBytes();
    }

    private static void testManifestDigestWithValidBytes() throws NoSuchAlgorithmException {
        byte[] validBytes = "Manifest-Version: 1.0\n\n".getBytes("UTF-8");
        ManifestDigester digester = new ManifestDigester(validBytes);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = digester.manifestDigest(md);
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest should not be null or empty for valid input");
        }
    }

    private static void testManifestDigestWithNullRawBytes() throws NoSuchAlgorithmException {
        ManifestDigester digester = new ManifestDigester(null);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            digester.manifestDigest(md);
            throw new RuntimeException("Expected IllegalArgumentException when rawBytes is null");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
