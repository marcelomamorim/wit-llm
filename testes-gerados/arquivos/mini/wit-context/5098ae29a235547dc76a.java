/*
 * @test
 * @summary Test JarFile(String name) constructor for null input and normal file input
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws Exception {
        testNullName();
        testValidFileName();
    }

    private static void testNullName() {
        try {
            new JarFile((String) null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException for null name", ioe);
        }
    }

    private static void testValidFileName() throws IOException {
        // Use a temporary empty file to test constructor
        File tempFile = null;
        JarFile jarFile = null;
        try {
            tempFile = File.createTempFile("testJarFile", ".jar");
            tempFile.deleteOnExit();
            jarFile = new JarFile(tempFile.getPath());
            if (jarFile == null) {
                throw new RuntimeException("JarFile constructor returned null");
            }
        } finally {
            if (jarFile != null) {
                jarFile.close();
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
