/*
 * @test
 * @summary Test ManifestEntryVerifier.update method behavior
 * @run main ManifestEntryVerifierWitupTest
 */

import java.util.jar.Manifest;
import sun.security.util.ManifestEntryVerifier;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.io.IOException;

public class ManifestEntryVerifierWitupTest {
    public static void main(String[] args) throws IOException {
        testUpdateWithSkipTrue();
        testUpdateWithDigests();
    }

    private static void testUpdateWithSkipTrue() throws IOException {
        ManifestEntryVerifier mev = new ManifestEntryVerifier(null);
        // skip is true because man is null
        // update should do nothing and not throw
        byte[] buffer = new byte[] {1, 2, 3, 4};
        mev.update(buffer, 0, buffer.length);
    }

    private static void testUpdateWithDigests() throws IOException {
        // Create a minimal manifest with an entry and digest attribute
        Manifest man = new Manifest();
        Attributes attr = new Attributes();
        // Use SHA-256 digest attribute name as example
        attr.putValue("SHA-256-Digest", "dGVzdA=="); // base64 for 'test'
        man.getEntries().put("testEntry", attr);

        ManifestEntryVerifier mev = new ManifestEntryVerifier(man);
        JarEntry je = new JarEntry("testEntry");
        mev.setEntry("testEntry", je);

        // After setEntry, skip should be false and digests list non-empty
        // update should call update on each digest without exception
        byte[] buffer = new byte[] {10, 20, 30, 40, 50};
        mev.update(buffer, 1, 3); // update with offset 1, length 3
    }
}
