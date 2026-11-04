/*
 * @test
 * @summary Test JarOutputStream(OutputStream, Manifest) constructor behavior
 * @run main JarOutputStreamWitupTest
 */

import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedOutputStream;

public class JarOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullManifestThrows();
        testValidManifestWritesEntry();
    }

    private static void testNullManifestThrows() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new JarOutputStream(out, null);
            throw new RuntimeException("Expected NullPointerException for null manifest");
        } catch (NullPointerException npe) {
            if (!"man".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testValidManifestWritesEntry() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Manifest man = new Manifest();
        JarOutputStream jos = new JarOutputStream(baos, man);
        jos.close();
        byte[] jarBytes = baos.toByteArray();
        if (jarBytes.length == 0) {
            throw new RuntimeException("JarOutputStream did not write any data for valid manifest");
        }
        // Basic sanity: the first entry should be the manifest entry
        // We cannot access private state, so we check the output stream content
        // The manifest entry name is "META-INF/MANIFEST.MF"
        String manifestName = JarFile.MANIFEST_NAME;
        String output = new String(jarBytes, "ISO-8859-1");
        if (!output.contains(manifestName)) {
            throw new RuntimeException("Output does not contain manifest entry name");
        }
    }
}
