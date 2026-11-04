/*
 * @test
 * @summary Test JarFile(String name) constructor behavior
 * @run main JarFileWitupTest
 */

import java.util.jar.JarFile;
import java.io.File;
import java.io.IOException;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with a known existing jar file (java.base.jar or rt.jar may not be accessible, so create a temp empty jar)
        File tempJar = File.createTempFile("test", ".jar");
        tempJar.deleteOnExit();

        // Create an empty jar file to ensure it exists and is a valid zip
        try (java.util.jar.JarOutputStream jos = new java.util.jar.JarOutputStream(
                new java.io.FileOutputStream(tempJar))) {
            // no entries
        }

        // Construct JarFile with valid jar file path
        JarFile jf = new JarFile(tempJar.getPath());
        if (jf == null) {
            throw new AssertionError("JarFile constructor returned null");
        }
        jf.close();

        // Test with a non-existent file path - should throw IOException
        boolean threw = false;
        try {
            new JarFile("nonexistentfile.jar");
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException for non-existent file");
        }
    }
}
