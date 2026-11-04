/*
 * @test
 * @summary Verifies JarInputStream(InputStream) reads the optional manifest and exposes the first entry through public API
 * @run main JarInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        byte[] withManifest = buildJar(true);
        try (JarInputStream jis = new JarInputStream(new ByteArrayInputStream(withManifest))) {
            Manifest m = jis.getManifest();
            if (m == null) {
                throw new RuntimeException("Manifest expected when META-INF/MANIFEST.MF is present");
            }
            String version = m.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION);
            if (!"1.0".equals(version)) {
                throw new RuntimeException("Unexpected Manifest-Version: " + version);
            }
            JarEntry e = jis.getNextEntry();
            if (e == null || !"a.txt".equals(e.getName())) {
                throw new RuntimeException("Expected entry a.txt after manifest, got: " + e);
            }
        }

        byte[] withoutManifest = buildJar(false);
        try (JarInputStream jis = new JarInputStream(new ByteArrayInputStream(withoutManifest))) {
            if (jis.getManifest() != null) {
                throw new RuntimeException("Manifest must be null when no manifest entry exists");
            }
            JarEntry e = jis.getNextEntry();
            if (e == null || !"a.txt".equals(e.getName())) {
                throw new RuntimeException("Expected first entry a.txt, got: " + e);
            }
        }

        try (JarInputStream jis = new JarInputStream(new ByteArrayInputStream(new byte[0]))) {
            if (jis.getManifest() != null) {
                throw new RuntimeException("Manifest must be null for empty input");
            }
            if (jis.getNextEntry() != null) {
                throw new RuntimeException("No entries expected for empty input");
            }
        }
    }

    private static byte[] buildJar(boolean withManifest) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JarOutputStream jos;
        if (withManifest) {
            Manifest m = new Manifest();
            m.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            jos = new JarOutputStream(bos, m);
        } else {
            jos = new JarOutputStream(bos);
        }
        jos.putNextEntry(new JarEntry("a.txt"));
        jos.write(new byte[] {'h', 'i'});
        jos.closeEntry();
        jos.close();
        return bos.toByteArray();
    }
}
