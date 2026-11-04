/*
 * @test
 * @summary Tests observable behavior of the JarFile(File, boolean) constructor
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        File jar = File.createTempFile("witup", ".jar");
        jar.deleteOnExit();

        Manifest mf = new Manifest();
        mf.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        byte[] data = "hello".getBytes("UTF-8");
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jar), mf)) {
            jos.putNextEntry(new JarEntry("a.txt"));
            jos.write(data);
            jos.closeEntry();
        }

        checkValid(jar, true);
        checkValid(jar, false);
        checkMissingFile(jar);
    }

    static void checkValid(File jar, boolean verify) throws IOException {
        try (JarFile jf = new JarFile(jar, verify)) {
            check(jar.getPath().equals(jf.getName()), "getName mismatch");
            check(jf.getManifest() != null, "manifest should be present");
            check(jf.getEntry("a.txt") != null, "entry a.txt should be present");
            check(jf.getEntry("missing.txt") == null, "unexpected entry");
        }
    }

    static void checkMissingFile(File jar) {
        File missing = new File(jar.getParentFile(), "does-not-exist-witup-" + System.nanoTime() + ".jar");
        check(!missing.exists(), "test file should not exist");
        try {
            new JarFile(missing, true).close();
            throw new RuntimeException("expected IOException for missing file");
        } catch (IOException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
