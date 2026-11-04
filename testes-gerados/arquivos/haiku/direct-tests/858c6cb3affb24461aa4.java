/*
 * @test
 * @summary Verify JarURLConnection.getContent returns the JarFile for a jar root URL, the entry content for an entry URL, and fails for a missing jar
 * @run main JarURLConnectionWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        File jar = File.createTempFile("witup", ".jar");
        try {
            try (JarOutputStream out = new JarOutputStream(new FileOutputStream(jar))) {
                out.putNextEntry(new JarEntry("hello.txt"));
                out.write("hello".getBytes("UTF-8"));
                out.closeEntry();
            }

            String base = "jar:" + jar.toURI().toURL() + "!/";

            // Root URL (no entry name): getContent must return the JarFile
            java.net.JarURLConnection root =
                (java.net.JarURLConnection) new URL(base).openConnection();
            Object rootContent = root.getContent();
            if (!(rootContent instanceof JarFile)) {
                throw new RuntimeException("expected JarFile for root, got: " + rootContent);
            }
            ((JarFile) rootContent).close();

            // Entry URL: getContent delegates to super.getContent and must be non-null
            java.net.JarURLConnection entry =
                (java.net.JarURLConnection) new URL(base + "hello.txt").openConnection();
            Object entryContent = entry.getContent();
            if (entryContent == null) {
                throw new RuntimeException("entry content must not be null");
            }
            if (entryContent instanceof InputStream) {
                ((InputStream) entryContent).close();
            }

            // Missing jar: connect() must fail with an IOException
            File missing = new File(jar.getPath() + ".missing");
            java.net.JarURLConnection bad =
                (java.net.JarURLConnection) new URL("jar:" + missing.toURI().toURL() + "!/").openConnection();
            boolean failed = false;
            try {
                bad.getContent();
            } catch (IOException expected) {
                failed = true;
            }
            if (!failed) {
                throw new RuntimeException("expected IOException for missing jar");
            }
        } finally {
            jar.delete();
        }
    }
}
