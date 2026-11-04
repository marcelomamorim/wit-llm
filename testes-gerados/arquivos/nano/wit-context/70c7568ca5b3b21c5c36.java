/*
 * @test
 * @summary Tests for java.util.jar.JarFile constructor with File, boolean, int parameters
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {

    public static void main(String[] args) throws IOException {
        testNullFile();
        testValidFile();
    }

    private static void testNullFile() {
        try {
            new JarFile((File) null, true, 0);
            System.out.println("testNullFile: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullFile: Passed");
        } catch (Exception e) {
            System.out.println("testNullFile: Failed - Unexpected exception: " + e);
        }
    }

    private static void testValidFile() {
        try {
            File tempFile = File.createTempFile("test", ".jar");
            tempFile.deleteOnExit();
            try (JarFile jar = new JarFile(tempFile, false, 0)) {
                System.out.println("testValidFile: Passed");
            }
        } catch (IOException e) {
            System.out.println("testValidFile: Failed - IOException: " + e);
        }
    }
}
