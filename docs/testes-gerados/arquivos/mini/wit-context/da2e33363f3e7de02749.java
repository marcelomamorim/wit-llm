/*
 * @test
 * @summary Test JarFile(File, boolean, int, Runtime.Version) constructor for null version and normal cases
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        testNullVersionThrowsNPE();
        testValidVersionDoesNotThrow();
    }

    private static void testNullVersionThrowsNPE() {
        File dummyFile = new File("dummy.jar");
        try {
            new JarFile(dummyFile, true, 0, null);
            throw new AssertionError("Expected NullPointerException for null version");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException", ioe);
        }
    }

    private static void testValidVersionDoesNotThrow() throws IOException {
        File dummyFile = new File("dummy.jar");
        // Use Runtime.Version.parse to create a valid version
        Runtime.Version version = Runtime.Version.parse("11");
        JarFile jarFile = new JarFile(dummyFile, false, 0, version);
        if (jarFile == null) {
            throw new AssertionError("JarFile constructor returned null");
        }
    }
}
