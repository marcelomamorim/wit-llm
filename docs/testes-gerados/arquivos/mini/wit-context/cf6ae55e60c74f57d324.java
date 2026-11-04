/*
 * @test
 * @summary Test constructor JarFile(File file) for null argument and normal file
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws Exception {
        testNullFileThrowsNPE();
        testValidFileOpensJarFile();
    }

    private static void testNullFileThrowsNPE() {
        try {
            new JarFile((File) null);
            throw new AssertionError("Expected NullPointerException for null File argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException instead of NullPointerException", ioe);
        }
    }

    private static void testValidFileOpensJarFile() throws IOException {
        // Create a temporary empty file to open as JarFile
        File tempFile = File.createTempFile("JarFileWitupTest", ".jar");
        tempFile.deleteOnExit();

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(tempFile);
            if (jarFile == null) {
                throw new AssertionError("JarFile constructor returned null");
            }
        } finally {
            if (jarFile != null) {
                jarFile.close();
            }
            tempFile.delete();
        }
    }
}
