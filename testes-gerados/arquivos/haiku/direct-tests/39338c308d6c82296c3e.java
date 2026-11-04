/*
 * @test
 * @summary Tests the public JarOutputStream(OutputStream, Manifest) constructor: manifest as first entry, manifest content, null manifest handling, and the no-manifest constructor.
 * @run main JarOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testManifestIsFirstEntry();
        testManifestContentIsWritten();
        testNullManifestThrowsNullPointerException();
        testNoManifestConstructorWritesNoEntries();
    }

    static Manifest newManifest() {
        Manifest m = new Manifest();
        m.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        return m;
    }

    static void testManifestIsFirstEntry() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (JarOutputStream jos = new JarOutputStream(bos, newManifest())) {
            // constructor writes the manifest entry
        }
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ZipEntry e = zis.getNextEntry();
        if (e == null || !JarFile.MANIFEST_NAME.equals(e.getName())) {
            throw new RuntimeException("First entry is not manifest: " + (e == null ? null : e.getName()));
        }
        zis.close();
    }

    static void testManifestContentIsWritten() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (JarOutputStream jos = new JarOutputStream(bos, newManifest())) {
            // no additional entries
        }
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ZipEntry e = zis.getNextEntry();
        if (e == null) {
            throw new RuntimeException("Manifest entry missing");
        }
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        byte[] buf = new byte[256];
        int n;
        while ((n = zis.read(buf)) != -1) {
            content.write(buf, 0, n);
        }
        zis.close();
        String s = new String(content.toByteArray(), "UTF-8");
        if (!s.contains("Manifest-Version: 1.0")) {
            throw new RuntimeException("Manifest content not written: " + s);
        }
    }

    static void testNullManifestThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new JarOutputStream(new ByteArrayOutputStream(), null);
        } catch (NullPointerException e) {
            thrown = true;
            if (!"man".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null manifest");
        }
    }

    static void testNoManifestConstructorWritesNoEntries() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (JarOutputStream jos = new JarOutputStream(bos)) {
            // no entries written
        }
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ZipEntry e = zis.getNextEntry();
        zis.close();
        if (e != null) {
            throw new RuntimeException("Expected no entries, found: " + e.getName());
        }
    }
}
