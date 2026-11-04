/*
 * @test
 * @summary Checks that ManifestDigester.manifestDigest resets the MessageDigest, digests the raw manifest bytes, and is repeatable
 * @modules java.base/sun.security.util
 * @run main ManifestDigesterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import sun.security.util.ManifestDigester;

public class ManifestDigesterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] raw = ("Manifest-Version: 1.0\r\nCreated-By: test\r\n\r\n"
                + "Name: a\r\nSHA-256-Digest: x\r\n\r\n")
                .getBytes(StandardCharsets.UTF_8);

        ManifestDigester digester = new ManifestDigester(raw);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] expected = MessageDigest.getInstance("SHA-256").digest(raw);

        byte[] first = digester.manifestDigest(sha);
        check(first.length == 32, "SHA-256 digest length must be 32");
        check(Arrays.equals(expected, first), "digest must match SHA-256 of raw bytes");

        byte[] second = digester.manifestDigest(sha);
        check(Arrays.equals(first, second), "repeated calls must return the same digest");

        MessageDigest dirty = MessageDigest.getInstance("SHA-256");
        dirty.update((byte) 1);
        check(Arrays.equals(expected, digester.manifestDigest(dirty)),
              "method must reset the MessageDigest before updating");

        ManifestDigester other = new ManifestDigester(new byte[] {'a'});
        byte[] otherDigest = other.manifestDigest(sha);
        check(!Arrays.equals(first, otherDigest),
              "different raw bytes must produce different digests");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
