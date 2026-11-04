/*
 * @test
 * @summary Verify public JarFile(File, boolean, int) opens jars, exposes name, entries, content and manifest, and fails with IOException for missing files
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarFileWitupTest {

    private static final int OPEN_READ = 1;

    public static void main(String[] args) throws Exception {
        File jar = File.createTempFile("witup", ".jar");
        jar.deleteOnExit();
        writeJar(jar);

        try (JarFile jf = new JarFile(jar, false, OPEN_READ)) {
            check(jar.getPath().equals(jf.getName()), "name mismatch");
            JarEntry e = jf.getJarEntry("a.txt");
            check(e != null, "entry a.txt missing");
            check("hello".equals(readEntry(jf, e)), "unexpected content");
            check(jf.getManifest() != null, "manifest missing");
        }

        try (JarFile jf = new JarFile(jar, true, OPEN_READ)) {
            check(jf.getEntry("a.txt") != null, "entry missing with verify=true");
        }

        File missing = new File(jar.getParentFile(), "witup-does-not-exist-" + System.nanoTime() + ".jar");
        try {
            new JarFile(missing, false, OPEN_READ);
            throw new AssertionError("expected IOException for missing file");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void writeJar(File f) throws IOException {
        Manifest mf = new Manifest();
        mf.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream out = new JarOutputStream(new FileOutputStream(f), mf)) {
            out.putNextEntry(new JarEntry("a.txt"));
            out.write("hello".getBytes(StandardCharsets.UTF_8));
            out.closeEntry();
        }
    }

    private static String readEntry(JarFile jf, JarEntry e) throws IOException {
        try (InputStream in = jf.getInputStream(e)) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }
}
