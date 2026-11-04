/*
 * @test
 * @summary Test JarInputStream(InputStream) constructor behavior
 * @run main JarInputStreamWitupTest
 */

import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.io.*;

public class JarInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyStream();
        testStreamWithManifest();
        testStreamWithMetaInfDirThenManifest();
    }

    private static void testEmptyStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        JarInputStream jis = new JarInputStream(bais);
        JarEntry entry = jis.getNextJarEntry();
        if (entry != null) {
            throw new RuntimeException("Expected no entries in empty stream");
        }
        jis.close();
    }

    private static void testStreamWithManifest() throws IOException {
        // Create a minimal JAR stream with a MANIFEST.MF entry as first entry
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(baos)) {
            zos.putNextEntry(new java.util.zip.ZipEntry("META-INF/MANIFEST.MF"));
            String manifestContent = "Manifest-Version: 1.0\n\n";
            zos.write(manifestContent.getBytes("UTF-8"));
            zos.closeEntry();
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        JarInputStream jis = new JarInputStream(bais);
        JarEntry entry = jis.getNextJarEntry();
        if (entry != null) {
            // The first entry after manifest should be returned
            if ("META-INF/MANIFEST.MF".equalsIgnoreCase(entry.getName())) {
                throw new RuntimeException("Manifest entry should be consumed internally, not returned by getNextJarEntry");
            }
        }
        jis.close();
    }

    private static void testStreamWithMetaInfDirThenManifest() throws IOException {
        // Create a minimal JAR stream with META-INF/ dir then MANIFEST.MF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(baos)) {
            zos.putNextEntry(new java.util.zip.ZipEntry("META-INF/"));
            zos.closeEntry();
            zos.putNextEntry(new java.util.zip.ZipEntry("META-INF/MANIFEST.MF"));
            String manifestContent = "Manifest-Version: 1.0\n\n";
            zos.write(manifestContent.getBytes("UTF-8"));
            zos.closeEntry();
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        JarInputStream jis = new JarInputStream(bais);
        JarEntry entry = jis.getNextJarEntry();
        if (entry != null) {
            // The first entry after manifest should be returned
            if ("META-INF/".equalsIgnoreCase(entry.getName()) || "META-INF/MANIFEST.MF".equalsIgnoreCase(entry.getName())) {
                throw new RuntimeException("Manifest and META-INF/ entries should be consumed internally, not returned by getNextJarEntry");
            }
        }
        jis.close();
    }
}
