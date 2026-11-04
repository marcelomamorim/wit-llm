/*
 * @test
 * @summary Verify ManifestDigester.manifestDigest returns the digest of the raw manifest bytes and resets the supplied MessageDigest
 * @modules java.base/sun.security.util
 * @run main ManifestDigesterWitupTest
 */

import java.security.MessageDigest;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import sun.security.util.ManifestDigester;

public class ManifestDigesterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] raw = "Manifest-Version: 1.0\r\nCreated-By: test\r\n\r\nName: a\r\nSHA-256: x\r\n\r\n".getBytes(StandardCharsets.UTF_8);
        byte[] expected = MessageDigest.getInstance("SHA-256").digest(raw);

        ManifestDigester digester = new ManifestDigester(raw);

        byte[] fresh = digester.manifestDigest(MessageDigest.getInstance("SHA-256"));
        check(Arrays.equals(expected, fresh), "fresh digest does not match digest of raw bytes");

        MessageDigest dirty = MessageDigest.getInstance("SHA-256");
        dirty.update("garbage".getBytes(StandardCharsets.UTF_8));
        byte[] afterDirty = digester.manifestDigest(dirty);
        check(Arrays.equals(expected, afterDirty), "digest was not reset before computing");

        byte[] again = digester.manifestDigest(dirty);
        check(Arrays.equals(expected, again), "repeated call is not idempotent");

        byte[] otherRaw = "Manifest-Version: 1.0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
        ManifestDigester other = new ManifestDigester(otherRaw);
        byte[] otherDigest = other.manifestDigest(MessageDigest.getInstance("SHA-256"));
        check(Arrays.equals(MessageDigest.getInstance("SHA-256").digest(otherRaw), otherDigest),
              "digest of second manifest does not match its raw bytes");
        check(!Arrays.equals(otherDigest, expected), "different manifests produced equal digests");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
