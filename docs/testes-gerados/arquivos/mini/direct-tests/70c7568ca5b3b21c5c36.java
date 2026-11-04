/*
 * @test
 * @summary Test JarFile(File, boolean, int) constructor basic behavior
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with a valid existing jar file path (use java.base module jar as a known jar)
        String javaHome = System.getProperty("java.home");
        File rtJar = new File(javaHome + "/lib/rt.jar");

        if (!rtJar.exists()) {
            // On some JDKs rt.jar may not exist, try alternative known jar
            File altJar = new File(javaHome + "/lib/modules");
            if (!altJar.exists()) {
                System.out.println("No known jar file found to test JarFile constructor, skipping test.");
                return;
            } else {
                testJarFileConstructor(altJar);
            }
        } else {
            testJarFileConstructor(rtJar);
        }

        // Test with a non-existent file to check IOException is thrown
        File nonExistent = new File("nonexistentfile.jar");
        try {
            new JarFile(nonExistent, false, 0);
            throw new RuntimeException("Expected IOException not thrown for non-existent file");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testJarFileConstructor(File file) throws IOException {
        JarFile jarFile = new JarFile(file, false, 0);
        if (!file.getAbsolutePath().equals(jarFile.getName())) {
            throw new RuntimeException("JarFile name does not match file path");
        }
        jarFile.close();
    }
}
