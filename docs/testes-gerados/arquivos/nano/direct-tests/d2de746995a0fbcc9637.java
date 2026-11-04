/*
 * @test
 * @summary Test manifestDigest method of ManifestDigester for correct digest computation.
 * @run main ManifestDigesterWitupTest
 */

import java.security.MessageDigest;
import sun.security.util.ManifestDigester;

public class ManifestDigesterWitupTest {

    public static void main(String[] args) throws Exception {
        testManifestDigestWithEmptyBytes();
        testManifestDigestWithSampleBytes();
    }

    private static void testManifestDigestWithEmptyBytes() throws Exception {
        byte[] emptyBytes = new byte[0];
        ManifestDigester digester = new ManifestDigester(emptyBytes);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = digester.manifestDigest(md);
        byte[] expectedDigest = md.digest(emptyBytes);
        assert java.util.Arrays.equals(digest, expectedDigest) : "Digest mismatch for empty bytes";
    }

    private static void testManifestDigestWithSampleBytes() throws Exception {
        byte[] sampleBytes = "Sample manifest content".getBytes("UTF-8");
        ManifestDigester digester = new ManifestDigester(sampleBytes);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = digester.manifestDigest(md);
        byte[] expectedDigest = md.digest(sampleBytes);
        assert java.util.Arrays.equals(digest, expectedDigest) : "Digest mismatch for sample bytes";
    }
}
