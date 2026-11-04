/*
 * @test
 * @summary Test JarFile(File, boolean) constructor basic behavior
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with a known existing jar file: use java.base module jar from system property
        String javaHome = System.getProperty("java.home");
        if (javaHome == null) {
            throw new RuntimeException("java.home system property not set");
        }
        File rtJar = new File(javaHome, "lib/rt.jar");
        if (!rtJar.exists()) {
            // On some JDKs rt.jar may not exist, try alternative
            rtJar = new File(javaHome, "jmods/java.base.jmod");
            if (!rtJar.exists()) {
                System.out.println("No known jar file found to test JarFile constructor; test skipped");
                return;
            }
        }

        // Construct JarFile with verify = true
        JarFile jarFile = new JarFile(rtJar, true);
        try {
            // Check that getName returns the expected file path
            String name = jarFile.getName();
            if (!name.equals(rtJar.getPath())) {
                throw new AssertionError("JarFile.getName() returned '" + name + "' instead of '" + rtJar.getPath() + "'");
            }

            // Check that entries enumeration is not empty
            if (!jarFile.entries().hasMoreElements()) {
                throw new AssertionError("JarFile entries enumeration is empty");
            }

            // Check that manifest is not null (most JDK jars have manifest)
            if (jarFile.getManifest() == null) {
                System.out.println("Warning: JarFile manifest is null (may be normal for this jar)");
            }

        } finally {
            jarFile.close();
        }

        // Test with verify = false
        JarFile jarFileNoVerify = new JarFile(rtJar, false);
        try {
            if (!jarFileNoVerify.getName().equals(rtJar.getPath())) {
                throw new AssertionError("JarFile.getName() with verify=false returned unexpected name");
            }
        } finally {
            jarFileNoVerify.close();
        }

        // Test with a non-existent file to check IOException thrown
        File nonExistent = new File("nonexistentfile.jar");
        try {
            new JarFile(nonExistent, true);
            throw new AssertionError("Expected IOException not thrown for non-existent file");
        } catch (IOException expected) {
            // Expected
        }
    }
}
