/*
 * @test
 * @summary Test ManifestDigester.manifestDigest with various MessageDigest instances
 * @run main ManifestDigesterWitupTest
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.security.util.ManifestDigester;

public class ManifestDigesterWitupTest {
    public static void main(String[] args) throws Exception {
        testManifestDigestWithSHA256();
        testManifestDigestWithEmptyBytes();
        testManifestDigestWithSHA1();
    }

    private static void testManifestDigestWithSHA256() throws Exception {
        byte[] data = "Test data for ManifestDigester".getBytes("UTF-8");
        ManifestDigester digester = new ManifestDigester(data);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = digester.manifestDigest(md);
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest should not be null or empty for SHA-256");
        }
    }

    private static void testManifestDigestWithEmptyBytes() throws Exception {
        byte[] empty = new byte[0];
        ManifestDigester digester = new ManifestDigester(empty);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = digester.manifestDigest(md);
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest should not be null or empty for empty input");
        }
    }

    private static void testManifestDigestWithSHA1() throws Exception {
        byte[] data = "Another test string".getBytes("UTF-8");
        ManifestDigester digester = new ManifestDigester(data);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = digester.manifestDigest(md);
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest should not be null or empty for SHA-1");
        }
    }
}
