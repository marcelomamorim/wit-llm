/*
 * @test
 * @summary Black-box checks that ManifestEntryVerifier.update is safe in skip and no-digest states
 * @modules java.base/sun.security.util
 * @run main ManifestEntryVerifierWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.Manifest;
import sun.security.util.ManifestEntryVerifier;

public class ManifestEntryVerifierWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = "hello".getBytes(StandardCharsets.UTF_8);

        ManifestEntryVerifier noManifest = new ManifestEntryVerifier(null);
        noManifest.update(data, 0, data.length);
        noManifest.setEntry("a.txt", new JarEntry("a.txt"));
        noManifest.update(data, 0, data.length);
        noManifest.setEntry(null, null);
        noManifest.update(data, 0, data.length);

        Manifest m = new Manifest(new ByteArrayInputStream(
                "Manifest-Version: 1.0\n\n".getBytes(StandardCharsets.UTF_8)));
        ManifestEntryVerifier missing = new ManifestEntryVerifier(m);
        missing.setEntry("missing.txt", new JarEntry("missing.txt"));
        missing.update(data, 0, data.length);
        missing.update(data, 1, 3);
        missing.update(data, 0, 0);

        ManifestEntryVerifier fresh = new ManifestEntryVerifier(m);
        fresh.update(new byte[0], 0, 0);
        fresh.update(data, data.length, 0);
    }
}
