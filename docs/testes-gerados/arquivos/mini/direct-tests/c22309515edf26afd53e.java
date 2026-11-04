/*
 * @test
 * @summary Test JarFile(String name, boolean verify) constructor behavior
 * @run main JarFileWitupTest
 */

import java.util.jar.JarFile;
import java.io.File;
import java.io.IOException;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with a valid existing jar file path (using java.base module jar as example)
        String javaBaseJar = System.getProperty("java.home") + "/lib/rt.jar";
        File f = new File(javaBaseJar);
        if (!f.exists()) {
            // On some JDKs rt.jar may not exist, try alternative
            javaBaseJar = System.getProperty("java.home") + "/lib/modules";
            f = new File(javaBaseJar);
        }

        // If file exists and is a file, test normal construction
        if (f.exists() && f.isFile()) {
            JarFile jf = new JarFile(javaBaseJar, false);
            if (jf == null) {
                throw new AssertionError("JarFile constructor returned null");
            }
            jf.close();
        }

        // Test with a non-existent file name - expect IOException
        String nonExistent = "nonexistentfile.jar";
        try {
            new JarFile(nonExistent, false);
            throw new AssertionError("Expected IOException for non-existent file");
        } catch (IOException e) {
            // Expected
        }

        // Test with a directory name - expect IOException
        String dirName = System.getProperty("java.io.tmpdir");
        try {
            new JarFile(dirName, false);
            throw new AssertionError("Expected IOException for directory input");
        } catch (IOException e) {
            // Expected
        }
    }
}
