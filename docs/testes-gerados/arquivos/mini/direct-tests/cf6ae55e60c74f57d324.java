/*
 * @test
 * @summary Test basic JarFile(File) constructor behavior
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with a known existing jar file (java.base module jar)
        // Use java.home system property to locate rt.jar or equivalent
        String javaHome = System.getProperty("java.home");
        if (javaHome == null) {
            throw new RuntimeException("java.home property not set");
        }

        // Try common locations for a jar file inside java.home
        File jarFile = new File(javaHome, "lib/rt.jar");
        if (!jarFile.exists()) {
            jarFile = new File(javaHome, "../lib/rt.jar");
        }

        if (!jarFile.exists()) {
            // fallback: try java.base module location (not guaranteed)
            // Just create a temp empty file to test constructor throws IOException
            File tmp = File.createTempFile("empty", ".jar");
            tmp.deleteOnExit();
            try {
                new JarFile(tmp);
                throw new RuntimeException("Expected IOException for empty jar file");
            } catch (IOException e) {
                // expected
            }
            return;
        }

        JarFile jf = null;
        try {
            jf = new JarFile(jarFile);
            if (jf == null) {
                throw new RuntimeException("JarFile constructor returned null");
            }
            // Check that the name matches
            if (!jarFile.getAbsolutePath().equals(jf.getName())) {
                throw new RuntimeException("JarFile name mismatch");
            }
        } finally {
            if (jf != null) {
                jf.close();
            }
        }
    }
}
