/*
 * @test
 * @summary Test JarOutputStream(OutputStream, Manifest) constructor behavior
 * @run main JarOutputStreamWitupTest
 */

import java.util.jar.*;
import java.util.zip.*;
import java.io.*;

public class JarOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullManifestThrows();
        testValidManifestWritesEntry();
    }

    private static void testNullManifestThrows() {
        try {
            new JarOutputStream(new ByteArrayOutputStream(), null);
            throw new RuntimeException("Expected NullPointerException for null manifest");
        } catch (NullPointerException e) {
            if (!"man".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testValidManifestWritesEntry() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Manifest manifest = new Manifest();
        // Add a simple attribute to manifest main attributes
        manifest.getMainAttributes().putValue("Manifest-Version", "1.0");

        JarOutputStream jos = new JarOutputStream(baos, manifest);
        jos.close();

        byte[] jarBytes = baos.toByteArray();
        if (jarBytes.length == 0) {
            throw new RuntimeException("JarOutputStream did not write any data");
        }

        // Check that the first entry is the manifest entry by reading back
        ByteArrayInputStream bais = new ByteArrayInputStream(jarBytes);
        java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(bais);
        ZipEntry entry = zis.getNextEntry();
        if (entry == null) {
            throw new RuntimeException("No entries found in written JAR");
        }
        if (!JarFile.MANIFEST_NAME.equals(entry.getName())) {
            throw new RuntimeException("First entry is not MANIFEST.MF: " + entry.getName());
        }
        zis.close();
    }
}
