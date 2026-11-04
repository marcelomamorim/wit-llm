/*
 * @test
 * @summary Verifies that ManifestEntryVerifier.update returns without throwing when the entry is skipped
 * @modules java.base/sun.security.util
 * @run main ManifestEntryVerifierWitupTest
 */

import java.util.jar.Manifest;
import sun.security.util.ManifestEntryVerifier;

public class ManifestEntryVerifierWitupTest {
    public static void main(String[] args) throws Exception {
        ManifestEntryVerifier v = new ManifestEntryVerifier(new Manifest());
        v.update(null, 0, 5);
        v.update(new byte[2], 1, 10);

        v.setEntry("missing.txt", null);
        v.update(null, 0, 3);
        v.update(new byte[4], -1, -1);

        ManifestEntryVerifier nullMan = new ManifestEntryVerifier(null);
        nullMan.setEntry("a.txt", null);
        nullMan.update(null, -1, -1);
    }
}
