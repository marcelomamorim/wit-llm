/* @test    @summary Test constructor of JarOutputStream with Manifest parameter    @run main JarOutputStreamWitupTest */

import java.io.*;
import java.util.jar.*;

public class JarOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithManifest();
        testConstructorWithNullManifest();
    }

    private static void testConstructorWithManifest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Manifest manifest = new Manifest();
        try (JarOutputStream jos = new JarOutputStream(baos, manifest)) {
            // Verify that the output stream contains the manifest entry
            byte[] data = baos.toByteArray();
            String content = new String(data, "ISO-8859-1");
            if (!content.contains(JarFile.MANIFEST_NAME)) {
                throw new AssertionError("Manifest entry not found in output stream");
            }
        }
    }

    private static void testConstructorWithNullManifest() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new JarOutputStream(baos, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"man".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
