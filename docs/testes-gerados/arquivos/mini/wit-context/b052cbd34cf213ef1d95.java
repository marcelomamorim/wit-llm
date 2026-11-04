/*
 * @test
 * @summary Test constructor JarFile(File file, boolean verify) for null argument and normal usage
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        testNullFileThrowsNPE();
        testValidFileNoVerify();
    }

    private static void testNullFileThrowsNPE() {
        try {
            new JarFile((File) null, true);
            throw new AssertionError("Expected NullPointerException for null File argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException for null File argument", ioe);
        }
    }

    private static void testValidFileNoVerify() throws IOException {
        File tmpFile = null;
        JarFile jarFile = null;
        try {
            tmpFile = File.createTempFile("testJarFile", ".jar");
            // The file is empty, but constructor should succeed (no verify requested)
            jarFile = new JarFile(tmpFile, false);
            if (jarFile == null) {
                throw new AssertionError("JarFile constructor returned null");
            }
        } finally {
            if (jarFile != null) {
                jarFile.close();
            }
            if (tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }
}
