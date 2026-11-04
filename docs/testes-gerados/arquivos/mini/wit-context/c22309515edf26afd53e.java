/*
 * @test
 * @summary Test JarFile(String name, boolean verify) constructor for null and valid inputs
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testValidNameSucceeds();
    }

    private static void testNullNameThrowsNPE() {
        try {
            new JarFile((String) null, true);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException for null name", ioe);
        }
    }

    private static void testValidNameSucceeds() throws IOException {
        // Use a known existing file or create a temporary empty jar file
        // Since we cannot create a real jar file here, use the current java.base module path or a known file
        // But to keep test deterministic and minimal, use a temporary empty file
        File tempFile = null;
        try {
            tempFile = File.createTempFile("empty", ".jar");
            tempFile.deleteOnExit();
            JarFile jarFile = new JarFile(tempFile.getAbsolutePath(), false);
            jarFile.close();
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
